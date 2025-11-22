package game.entities.ghosts;

import game.gameconfig.LevelConfig;
import game.ghostStrategies.BlinkyStrategy;
import game.ghostVisitor.GhostVisitor;

//Blinky(빨간 유령)의 구체적인 클래스
public class Blinky extends Ghost {
    public Blinky(int xPos, int yPos, LevelConfig levelConfig) { // <-- 변경됨
        super(xPos, yPos, "blinky.png", levelConfig); // <-- 변경됨
        setStrategy(new BlinkyStrategy());
    }

    @Override
    public void accept(GhostVisitor visitor) {
        visitor.visit(this);
    }
}
