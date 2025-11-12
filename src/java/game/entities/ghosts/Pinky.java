package game.entities.ghosts;

import game.ghostStrategies.PinkyStrategy;

//Pinky(분홍 유령)의 구체적인 클래스
public class Pinky extends Ghost {
    public Pinky(int xPos, int yPos) {
        super(xPos, yPos, "pinky.png");
        setStrategy(new PinkyStrategy());
    }
}
