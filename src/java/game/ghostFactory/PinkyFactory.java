package game.ghostFactory;

import game.entities.ghosts.Ghost;
import game.entities.ghosts.Pinky;
import game.gameconfig.LevelConfig;

//Pinky 유령을 생성하기 위한 구체적인 팩토리
public class PinkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos, LevelConfig levelConfig) {
        return new Pinky(xPos, yPos, levelConfig);
    }
}
