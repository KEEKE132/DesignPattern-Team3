package game.gameStates;

import game.GameLauncher;
import game.GameplayPanel;
import game.utils.KeyHandler;

import java.awt.*;

public class MainMenuState implements GameState {
    private final GameplayPanel gameplayPanel;

    public MainMenuState(GameplayPanel gameplayPanel) {
        this.gameplayPanel = gameplayPanel;
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics2D g) {
        // 검은 배경
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameplayPanel.width, GameplayPanel.height);

        // "PAC-MAN" 텍스트
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String msg = "PAC-MAN";
        int x = (GameplayPanel.width - g.getFontMetrics().stringWidth(msg)) / 2;
        int y = GameplayPanel.height / 2;
        g.drawString(msg, x, y);

        // 안내문구
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        msg = "ENTER: Start  |  Esc: Exit";
        x = (GameplayPanel.width - g.getFontMetrics().stringWidth(msg)) / 2;
        g.drawString(msg, x, y + 80);
    }

    @Override
    public void input(KeyHandler k) {
        // 'Enter': 게임 시작
        if (k.k_enter.isPressedOnce()) {
            gameplayPanel.setState(new PlayingState(gameplayPanel));
        }

        // 'Esc': 게임 종료
        if (k.k_escape.isPressedOnce()) System.exit(0);
    }

    @Override
    public void onEnter() {
        // MainMenu로 진입 시 초기화
        gameplayPanel.getLevelManager().reset(); // 1. 데이터 초기화
        GameLauncher.getUIPanel().refreshScore(); // 2. UIPanel 점수판 초기화
    }

    @Override
    public void onExit() {}
}
