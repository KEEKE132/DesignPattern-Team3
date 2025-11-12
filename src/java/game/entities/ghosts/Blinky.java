package game.entities.ghosts;

import game.ghostStrategies.BlinkyStrategy;

//Blinky(빨간 유령)의 구체적인 클래스
public class Blinky extends Ghost {
    public Blinky(int xPos, int yPos) {
        super(xPos, yPos, "blinky.png");
        setStrategy(new BlinkyStrategy());
    }
}
