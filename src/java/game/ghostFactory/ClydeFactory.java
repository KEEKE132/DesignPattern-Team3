package game.ghostFactory;

import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;

//Clyde 유령을 생성하기 위한 구체적인 팩토리
public class ClydeFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Clyde(xPos, yPos);
    }
}