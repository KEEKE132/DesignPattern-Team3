package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;
import game.entities.ghosts.Ghost;
import game.utils.Utils;

//Clyde(노란 유령)의 구체적인 전략
public class ClydeStrategy implements IGhostStrategy{
    private Ghost ghost;
    public ClydeStrategy(Ghost ghost) {
        this.ghost = ghost;
    }

    //Clyde는 8칸 반경 밖에 있으면 팩맨을 직접 목표로 하고, 그렇지 않으면(너무 가까우면) 자신의 휴식 위치를 목표로 합니다.
    @Override
    public int[] getChaseTargetPosition() {
        //유령과 팩맨과의 직선 거리 >= 256픽셀?
        if (Utils.getDistance(ghost.getxPos(), ghost.getyPos(), Game.getPacman().getxPos(), Game.getPacman().getyPos()) >= 256) {
            int[] position = new int[2];
            position[0] = Game.getPacman().getxPos();
            position[1] = Game.getPacman().getyPos();
            return position;
        }else{
            return getScatterTargetPosition();
        }
    }

    //휴식 시, Clyde는 좌측 하단 칸을 목표로 합니다.
    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = 0;
        position[1] = GameplayPanel.height;
        return position;
    }
}
