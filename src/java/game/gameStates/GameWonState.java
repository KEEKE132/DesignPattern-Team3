package game.gameStates;

import game.GameplayPanel;
import game.utils.KeyHandler;

import java.awt.*;

public class GameWonState implements GameState {
    private final GameplayPanel gameplayPanel;

    public GameWonState(GameplayPanel gameplayPanel) {
        this.gameplayPanel = gameplayPanel;
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics2D g) {
        // 검은 배경
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameplayPanel.width, GameplayPanel.height);

        // "YOU WIN!" 텍스트
        g.setColor(new Color(255, 184, 81));
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String msg = "YOU WIN!";
        int x = (GameplayPanel.width - g.getFontMetrics().stringWidth(msg)) / 2;
        int y = GameplayPanel.height / 2;
        g.drawString(msg, x, y);

        // 최종 점수 표시
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String scoreMsg = "Total Score: " + gameplayPanel.getScoreManager().getCurrentScore();
        x = (GameplayPanel.width - g.getFontMetrics().stringWidth(scoreMsg)) / 2;
        g.drawString(scoreMsg, x, y + 80);

        // 안내 문구
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        msg = "Esc: Menu";
        x = (GameplayPanel.width - g.getFontMetrics().stringWidth(msg)) / 2;
        g.drawString(msg, x, GameplayPanel.height - 100);
    }

    @Override
    public void input(KeyHandler k) {
        // 'Esc': 메인 메뉴로 이동
        if (k.k_escape.isPressedOnce()) gameplayPanel.setState(new MainMenuState(gameplayPanel));
    }

    @Override
    public void onEnter() {}

    @Override
    public void onExit() {}
}
