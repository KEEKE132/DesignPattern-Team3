package game;

import game.entities.*;
import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import game.gameconfig.LevelConfig;
import game.ghostFactory.*;
import game.ghostStates.EatenMode;
import game.ghostStates.FrightenedMode;
import game.ghostVisitor.GhostVisitor;
import game.ghostVisitor.StartLineVisitor;
import game.utils.CollisionDetector;
import game.utils.CsvReader;
import game.utils.KeyHandler;

import java.awt.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

//게임 그 자체를 관리하는 클래스
public class Game implements Observer {
    //창(window)에 존재하는 여러 엔티티들의 목록을 만들기 위함
    private List<Entity> objects = new ArrayList(); //생성된 모든 객체를 저장
    private List<Ghost> ghosts = new ArrayList(); //유령만 저장
    private static List<Wall> walls = new ArrayList(); //벽만 저장

    private static Pacman pacman;
    private static Blinky blinky;

    private static boolean firstInput = false;

    private int totalGumsOnMap = 0; //맵의 총 팩껌 개수

    public Game(LevelConfig levelConfig){
        //게임 초기화
        //레벨(맵)의 CSV 파일 로드
        List<List<String>> data = null;
        try {
            //CsvReader를 사용해 레벨 csv 파일을 읽어 2차원 리스트(data)로 가져옵니다.
            data = new CsvReader().parseCsv(getClass().getClassLoader().getResource(levelConfig.getLevelMap()).toURI()); // <-- 변경
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        int cellsPerRow = data.get(0).size();
        int cellsPerColumn = data.size();
        int cellSize = 8;

        CollisionDetector collisionDetector = new CollisionDetector(this);
        AbstractGhostFactory abstractGhostFactory = null;

        //레벨은 "그리드"를 가지고 있으며, CSV 파일의 각 칸에 대해, 존재하는 문자에 따라 그리드의 한 칸에 특정 엔티티를 표시합니다.
        for(int xx = 0 ; xx < cellsPerRow ; xx++) { //맵의 모든 칸(xx, yy)을 순회
            for(int yy = 0 ; yy < cellsPerColumn ; yy++) {
                String dataChar = data.get(yy).get(xx);
                if (dataChar.equals("x")) { //벽 생성
                    objects.add(new Wall(xx * cellSize, yy * cellSize));
                }else if (dataChar.equals("P")) { //팩맨 생성
                    // Pacman 생성 시 levelConfig 주입
                    pacman = new Pacman(xx * cellSize, yy * cellSize, levelConfig); // <-- 변경됨
                    pacman.setCollisionDetector(collisionDetector);

                    //팩맨의 여러 옵저버(구독자)들 등록
                    pacman.registerObserver(GameLauncher.getUIPanel()); //UIPanel를 팩맨의 첫 번째 구독자로 등록
                    pacman.registerObserver(this); //Game 클래스 자신을 팩맨의 두 번째 구독자로 등록
                }else if (dataChar.equals("b") || dataChar.equals("p") || dataChar.equals("i") || dataChar.equals("c")) { //여러 팩토리(Factory)들을 사용하여 유령 생성
                    switch (dataChar) {
                        case "b":
                            abstractGhostFactory = new BlinkyFactory();
                            break;
                        case "p":
                            abstractGhostFactory = new PinkyFactory();
                            break;
                        case "i":
                            abstractGhostFactory = new InkyFactory();
                            break;
                        case "c":
                            abstractGhostFactory = new ClydeFactory();
                            break;
                    }

                    // Ghost 생성 시 levelConfig 주입
                    Ghost ghost = abstractGhostFactory.makeGhost(xx * cellSize, yy * cellSize, levelConfig); // <-- 변경됨
                    ghosts.add(ghost);
                }else if (dataChar.equals(".")) { //팩껌(PacGum) 생성
                    objects.add(new PacGum(xx * cellSize, yy * cellSize));
                    totalGumsOnMap++;
                }else if (dataChar.equals("o")) { //슈퍼팩껌(SuperPacGum) 생성
                    objects.add(new SuperPacGum(xx * cellSize, yy * cellSize));
                    totalGumsOnMap++;
                }else if (dataChar.equals("-")) { //유령의 집 벽 생성
                    objects.add(new GhostHouse(xx * cellSize, yy * cellSize));
                }
            }
        }
        objects.add(pacman);
        objects.addAll(ghosts);

        for (Entity o : objects) {
            if (o instanceof Wall) {
                walls.add((Wall) o);
            }
        }

        GhostVisitor startLineVisitor = new StartLineVisitor();
        for(Ghost g : ghosts) {
            g.accept(startLineVisitor);
        }
    }

    public static List<Wall> getWalls() {
        return walls;
    }

    public List<Entity> getEntities() {
        return objects;
    }

    //모든 엔티티 업데이트
    public void update() {
        for (Entity o: objects) {
            if (!o.isDestroyed()) o.update();
        }
    }

    //입력(input) 관리
    public void input(KeyHandler k) {
        pacman.input(k);
    }

    //모든 엔티티 렌더링(그리기)
    public void render(Graphics2D g) {
        for (Entity o: objects) {
            if (!o.isDestroyed()) o.render(g);
        }
    }

    public static Pacman getPacman() {
        return pacman;
    }
    public static Blinky getBlinky() {
        return blinky;
    }

    //Game(이 클래스)은 팩맨이 팩껌, 슈퍼팩껌, 또는 유령과 접촉했을 때 알림을 받습니다.
    @Override
    public void updatePacGumEaten(PacGum pg) {
        pg.destroy(); //팩껌은 팩맨이 먹었을 때 파괴됩니다.
        totalGumsOnMap--;
        checkLevelClear();
    }

    @Override
    public void updateSuperPacGumEaten(SuperPacGum spg) {
        spg.destroy(); //슈퍼팩껌은 팩맨이 먹었을 때 파괴됩니다.
        totalGumsOnMap--;
        for (Ghost gh : ghosts) {
            gh.getState().superPacGumEaten(); //슈퍼팩껌이 먹혔을 때 특별한 전환(transition)이 존재한다면, 유령의 상태가 바뀝니다.
        }
        checkLevelClear();
    }

    //팩껌을 다 먹었는지 확인하고 게임 종료
    private void checkLevelClear() {
        if (totalGumsOnMap == 0) {
            System.out.println("YOU WIN!");
            System.out.println("Score : " + GameLauncher.getUIPanel().getScore());
            System.exit(0);
        }
    }

    @Override
    public void updateGhostCollision(Ghost gh) {
        if (gh.getState() instanceof FrightenedMode) {
            gh.getState().eaten(); //유령이 먹혔을 때 특별한 전환(transition)이 존재한다면, 그 상태가 결과에 따라 바뀝니다.
        }else if (!(gh.getState() instanceof EatenMode)) {
            System.out.println("Game over !\nScore : " + GameLauncher.getUIPanel().getScore()); //팩맨이 겁먹지도(frightened), 먹히지도(eaten) 않은 유령과 접촉했을 때, 게임 오버입니다!
            System.exit(0); //TODO
        }
    }

    public static void setFirstInput(boolean b) {
        firstInput = b;
    }

    public static boolean getFirstInput() {
        return firstInput;
    }
}
