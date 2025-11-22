package game.entities.items;

import game.Game;
import game.entities.Pacman;

public class HasteItem extends Item {

    // 속도 증가 배율
    private final double SPEED_MULTIPLIER = 2;

    // 지속 시간
    private final long DURATION_MS = 5000;

    public HasteItem(int xPos, int yPos) {
        super(xPos, yPos, "img/haste.png");
    }

    @Override
    public boolean isRequiredToClear() {
        return false;
    }

    @Override
    public void onEaten(Game game) {
        Pacman pacman = Game.getPacman();

        // 기존 팩맨 속도
        int originalSpeed = pacman.getSpd();

        // 팩맨 속도 증가
        int newSpeed = Math.min((int) (originalSpeed * SPEED_MULTIPLIER),8);
        pacman.setSpd(newSpeed);

        // 일정 시간 후에 속도를 되돌리기 위한 타이머
        new Thread(() -> {
            try {
                // 5초간 대기
                Thread.sleep(DURATION_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 5. 시간이 지나면 원래 속도로 복구합니다.
                // (게임이 종료되지 않았고 팩맨이 존재하는 경우에만)
                if (Game.getPacman() != null && !game.isGameOver()) {
                    pacman.setSpd(originalSpeed);
                }
            }
        }).start();
    }
}
