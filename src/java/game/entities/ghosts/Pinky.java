package game.entities.ghosts;

import game.gameconfig.LevelConfig;
import game.ghostStrategies.PinkyStrategy;

//Pinky(분홍 유령)의 구체적인 클래스
public class Pinky extends Ghost {
    public Pinky(int xPos, int yPos, LevelConfig levelConfig) { // <-- 변경됨
        super(xPos, yPos, "pinky.png", levelConfig); // <-- 변경됨
        setStrategy(new PinkyStrategy());
    }
}
