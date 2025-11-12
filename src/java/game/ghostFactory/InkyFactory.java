package game.ghostFactory;

import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;

//Inky 유령을 생성하기 위한 구체적인 팩토리
public class InkyFactory extends AbstractGhostFactory {
    @Override
    public Ghost makeGhost(int xPos, int yPos) {
        return new Inky(xPos, yPos);
    }
}
