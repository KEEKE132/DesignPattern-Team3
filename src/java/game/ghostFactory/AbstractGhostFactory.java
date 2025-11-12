package game.ghostFactory;

import game.entities.ghosts.*;

//서로 다른 생성자로부터 각기 다른 구체적인(concrete) 유령들을 생성하기 위한 추상 팩토리
//주석에 개발자가 "Abstract Factory"라고 적어두었지만, 이는 개발자의 용어 혼동이며 이 코드는 '팩토리 메서드'입니다.
public abstract class AbstractGhostFactory {
    public abstract Ghost makeGhost(int xPos, int yPos);
}

