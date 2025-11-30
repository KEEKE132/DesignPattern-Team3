package game.entities.items;

import game.Game;
import game.entities.Pacman;

public class HasteItem extends Item {
    // 60 프레임
    private static final int FPS = 60;

    // 지속 시간 (프레임)
    private static final int DURATION_FRAMES = 5 * FPS;

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

        // 스레드 없이 팩맨에게 로직 위임
        if (pacman != null) {
            pacman.applyHaste(DURATION_FRAMES);
        }
    }
}
