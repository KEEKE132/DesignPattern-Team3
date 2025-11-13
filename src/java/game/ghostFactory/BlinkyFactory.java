package game.ghostFactory;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Ghost;
import game.gameconfig.LevelConfig;

//Blinky 유령을 생성하기 위한 구체적인 팩토리
public class BlinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos, LevelConfig levelConfig) { // <-- 변경됨
        return new Blinky(xPos, yPos, levelConfig); // <-- 변경됨
    }
}
