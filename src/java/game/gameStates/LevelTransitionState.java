package game.gameStates;

import game.GameplayPanel;
import game.utils.KeyHandler;

import java.awt.*;

public class LevelTransitionState implements GameState {
    private static final int TRANSITION_TIME = 120; // 2초 (60fps * 2)

    private final GameplayPanel gameplayPanel;
    private int timer;

    public LevelTransitionState(GameplayPanel gameplayPanel) {
        this.gameplayPanel = gameplayPanel;
        timer = 0;
    }

    @Override
    public void update() {
        timer++;
        // 일정 시간이 지나면 다음 레벨 게임 시작
        if (timer >= TRANSITION_TIME) {
            gameplayPanel.setState(new PlayingState(gameplayPanel));
        }
    }

    @Override
    public void render(Graphics2D g) {
        // 검은 배경
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameplayPanel.width, GameplayPanel.height);

        // "LEVEL X" 텍스트
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        String msg = "LEVEL " + gameplayPanel.getLevelManager().getCurrentLevelNumber();
        int x = (GameplayPanel.width - g.getFontMetrics().stringWidth(msg)) / 2;
        int y = GameplayPanel.height / 3;
        g.drawString(msg, x, y + 10);

        // 카운트다운 숫자 그리기 (3 -> 2 -> 1)
        // 남은 시간(초) 계산: (총 시간 - 현재 시간) / 60
        // Math.ceil을 사용하여 2.9초 -> 3, 1.1초 -> 2, 0.1초 -> 1로 표시
        int countdown = (int) Math.ceil((TRANSITION_TIME - timer) / 60.0);

        if (countdown > 0) {
            msg = String.valueOf(countdown);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 60));

            x = (GameplayPanel.width - g.getFontMetrics().stringWidth(msg)) / 2;
            y = GameplayPanel.height / 2 + 50;
            g.drawString(msg, x, y);
        }
    }

    @Override
    public void input(KeyHandler k) {}

    @Override
    public void onEnter() {}

    @Override
    public void onExit() {}
}
