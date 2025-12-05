package game.gameStates;

import game.Game;
import game.GameplayPanel;
import game.gameconfig.LevelManager;
import game.utils.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class PlayingState implements GameState {
    private final GameplayPanel gameplayPanel;
    private Game game;
    private Image backgroundImage;

    public PlayingState(GameplayPanel gameplayPanel) {
        this.gameplayPanel = gameplayPanel;

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("img/background.png")));
        } catch (IOException e) {
            System.err.println("배경 이미지를 로드할 수 없습니다!");
        }
    }

    @Override
    public void update() {
        game.update();

        // 승리/패배 조건 확인
        if (game.isLevelCleared()) {
            LevelManager levelManager = gameplayPanel.getLevelManager();

            // 다음 레벨이 있는지 확인
            if (levelManager.hasNextLevel()) {
                levelManager.moveToNextLevel(); // 다음 레벨로 이동
                gameplayPanel.setState(new LevelTransitionState(gameplayPanel));
            } else {
                // 더 이상 레벨이 없으면 승리
                gameplayPanel.setState(new GameWonState(gameplayPanel));
            }
        } else if (game.isGameOver()) {
            gameplayPanel.setState(new GameOverState(gameplayPanel));
        }
    }

    @Override
    public void render(Graphics2D g) {
        // 배경맵
        g.drawImage(backgroundImage, 0, 0, GameplayPanel.width, GameplayPanel.height, null);
        game.render(g);
    }

    @Override
    public void input(KeyHandler k) {
        if (k.k_p.isPressedOnce()) {
            // 현재 상태(this)를 저장하면서 PausedState로 전환
            gameplayPanel.setState(new PausedState(gameplayPanel, this));
            return; // 더 이상 입력 처리하지 않고 리턴
        }

        // 첫 입력 감지 및 게임 시작 플래그 설정
        if (!Game.getFirstInput()) {
            if (k.k_up.isPressedOnce() || k.k_down.isPressedOnce() || k.k_left.isPressedOnce() || k.k_right.isPressedOnce()) {
                Game.setFirstInput(true); // Game 객체 내의 'firstInput' 변수가 true로 설정되면서 유령들이 움직이기 시작
            }
        }

        game.input(k); // 팩맨 이동 처리
    }

    @Override
    public void onEnter() {
        // Game 객체 생성 (start 또는 Restart 시에만)
        // 일시정지 후 복귀 시에는 game 객체가 이미 존재하므로(not-null) if문은 건너뜀
        if (game == null) {
            game = createGame();
            Game.setFirstInput(false);
        }
    }

    @Override
    public void onExit() {}

    protected Game createGame() {
        return new Game(gameplayPanel.getLevelManager(), gameplayPanel.getScoreManager());
    }
}
