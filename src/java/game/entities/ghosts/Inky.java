package game.entities.ghosts;

import game.Game;
import game.ghostStrategies.InkyStrategy;

//Inky(파란 유령)의 구체적인 클래스
public class Inky extends Ghost {
    public Inky(int xPos, int yPos) {
        super(xPos, yPos, "inky.png");
        setStrategy(new InkyStrategy(Game.getBlinky())); //Blinky의 정보를 받아갑니다. (오리지널 팩맨 게임에서 Inky의 AI는 Blinky의 위치를 참조하여 팩맨을 포위하기 때문에, 이를 정확히 구현한 것입니다.)
    }
}