package game;

import game.gameStates.GameState;
import game.gameStates.MainMenuState;
import game.gameconfig.LevelManager;
import game.utils.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//"게임 영역" 패널
public class GameplayPanel extends JPanel implements Runnable {
    public static int width;
    public static int height;
    private Thread thread;
    private boolean running = false;

    private BufferedImage img;
    private Graphics2D g;

    private final LevelManager levelManager; // 레벨 관리 객체 (Game을 생성할 때 필요)
    private KeyHandler key; // KeyHandler는 상태 객체들이 공유
    private GameState currentState; // 현재 상태 (메인 메뉴, 플레이 중, 게임 승리, 게임 오버)

    // 생성자가 LevelManager를 주입받도록 변경
    public GameplayPanel(int width, int height, LevelManager levelManager) throws IOException {
        GameplayPanel.width = width;
        GameplayPanel.height = height;
        this.levelManager = levelManager;

        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

        // (배경 이미지 로드는 PlayingState로 이동했으므로 삭제됨)
    }

    // 상태 전환 메서드
    public void setState(GameState newState) {
        if (currentState != null) currentState.onExit();  // 이전 상태 정리

        if (key != null) key.reset(); // 상태 전환 시 키 입력 초기화 (이전 입력이 남는 것 방지)

        currentState = newState;
        currentState.onEnter(); // 새 상태 초기화
    }

    // 상태 클래스들이 설정값에 접근하기 위해 사용
    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void quitGame() {
        System.exit(0);
    }

    public void resetGame() {
        levelManager.reset(); // 1. 데이터 초기화
        GameLauncher.getUIPanel().refreshScore(); // 2. UIPanel 점수판 초기화
    }

    @Override
    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    // 게임 초기화
    public void init() {
        running = true;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
        key = new KeyHandler(this);

        setState(new MainMenuState(this)); // 초기 상태를 '메인 메뉴'로 설정
    }

    // 게임 로직 업데이트 (현재 상태에게 위임)
    public void update() {
        if (currentState != null) currentState.update();
    }

    // 입력(input) 처리 (현재 상태에게 위임)
    public void input(KeyHandler key) {
        if (currentState != null) currentState.input(key);
    }

    // 화면 그리기 (현재 상태에게 위임)
    public void render() {
        if (g != null && currentState != null) currentState.render(g);
    }

    //게임 표시 : 렌더링이 완료된 그 이미지를 (화면에) 표시합니다.
    public void draw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();
    }

    @Override
    public void run() { // run()은 한 번만 실행
        init();

        //게임이 60FPS로 돌아가도록 하기 위함(튜토리얼 참조 : https://www.youtube.com/watch?v=LhUN3EKZiio)
        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ; //Time before update

        final int MUBR = 5; // Must update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60.0;
        final double TTBR = 1000000000 / TARGET_FPS; //Total time before render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        int oldFrameCount = 0;

        /*
         * 게임 루프 시작
         * 1. input(key): 사용자의 키 입력을 확인
         * 2. update(): 게임의 로직(캐릭터 이동, 충돌 감지 등)을 업데이트
         * 3. render(): 변경된 상태를 바탕으로 이미지를 그림
         * 4. draw(): 그려진 이미지를 실제 화면에 표시
         * 이 4가지 메서드가 while 문 안에서 1초에 약 60번(60 FPS)씩 반복 호출
         */
        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;
            while ((now - lastUpdateTime) > TBU && (updateCount < MUBR)) {
                input(key); // currentState에 위임
                update(); // currentState에 위임

                lastUpdateTime += TBU;
                updateCount++;
            }

            if (now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            render(); // currentState에 위임
            draw();

            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                if (frameCount != oldFrameCount) {
                    //System.out.println("FPS : " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while ((now - lastRenderTime < TTBR) && (now - lastUpdateTime < TBU)) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.err.println("ERROR yielding thread");
                }

                now = System.nanoTime();
            }
        }
    }
}