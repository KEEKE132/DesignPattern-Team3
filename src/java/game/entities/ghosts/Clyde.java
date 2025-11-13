package game.entities.ghosts;

import game.gameconfig.LevelConfig;
import game.ghostStrategies.ClydeStrategy;

//Clyde(노란 유령)의 구체적인 클래스
public class Clyde extends Ghost {
    public Clyde(int xPos, int yPos, LevelConfig levelConfig) { // <-- 변경됨
        super(xPos, yPos, "clyde.png", levelConfig); // <-- 변경됨
        setStrategy(new ClydeStrategy(this)); //자기 자신(this)의 정보를 전략 객체에게 넘겨줍니다. (아마도 팩맨과의 거리를 재야 하기 때문)
    }
}
