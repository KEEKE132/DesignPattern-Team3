package game.gameStates;

import game.GameplayPanel;
import game.utils.KeyHandler;

import java.awt.*;

public class PausedState implements GameState {
    private final GameplayPanel gameplayPanel;
    private final PlayingState previousState; // 멈추기 직전의 게임 상태를 저장

    public PausedState(GameplayPanel gameplayPanel, PlayingState previousState) {
        this.gameplayPanel = gameplayPanel;
        this.previousState = previousState; // 이전 상태(PlayingState)를 받아서 저장함
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics2D g) {
        // 이전 게임 화면(PlayingState)을 먼저 그립니다. (배경으로 깔아줌)
        previousState.render(g);

        // 반투명한 검은색 막을 덮어씌웁니다.
        g.setColor(new Color(0, 0, 0, 150)); // Alpha 150 (반투명)
        g.fillRect(0, 0, GameplayPanel.width, GameplayPanel.height);

        // "PAUSED" 텍스트
        g.setColor(new Color(255, 184, 255));
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String msg = "PAUSED";
        int x = (GameplayPanel.width - g.getFontMetrics().stringWidth(msg)) / 2;
        int y = GameplayPanel.height / 2;
        g.drawString(msg, x, y);

        // 안내문구
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        msg = "P: Resume  |  Esc: Menu";
        x = (GameplayPanel.width - g.getFontMetrics().stringWidth(msg)) / 2;
        g.drawString(msg, x, y + 80);
    }

    @Override
    public void input(KeyHandler k) {
        // 'P': 게임 재개 (저장해둔 previousState로 복귀)
        if (k.k_p.isPressedOnce()) gameplayPanel.setState(previousState);

        // 'Esc': 메인 메뉴로 이동
        if (k.k_escape.isPressedOnce()) gameplayPanel.setState(new MainMenuState(gameplayPanel));
    }

    @Override
    public void onEnter() {}

    @Override
    public void onExit() {}
}
