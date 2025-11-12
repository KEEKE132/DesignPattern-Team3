package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;

//Blinky(빨간 유령)의 구체적인 전략
public class BlinkyStrategy implements IGhostStrategy{
    //Blinky는 팩맨의 위치를 직접 목표로 합니다.
    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        position[0] = Game.getPacman().getxPos();
        position[1] = Game.getPacman().getyPos();
        return position;
    }

    //휴식 시, Blinky는 우측 상단 칸을 목표로 합니다.
    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = GameplayPanel.width;
        position[1] = 0;
        return position;
    }
}