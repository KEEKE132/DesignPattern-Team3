package game.ghostFactory;

import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.gameconfig.LevelConfig;

//Clyde 유령을 생성하기 위한 구체적인 팩토리
public class ClydeFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos, LevelConfig levelConfig) { // <-- 변경됨
        return new Clyde(xPos, yPos, levelConfig); // <-- 변경됨
    }
}