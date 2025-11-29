package game.ghostStates;

import game.entities.ghosts.Ghost;
import game.ghostVisitor.EatenLineVisitor;
import game.ghostVisitor.GhostVisitor;
import game.utils.Utils;
import jdk.jshell.execution.Util;

//팩맨이 슈퍼팩껌을 먹어 '겁에 질린' 구체적인 상태
public class FrightenedMode extends GhostState{
    public FrightenedMode(Ghost ghost) {
        super(ghost);
    }

    //유령이 먹혔을 때의 전환
    @Override
    public void eaten() {
        GhostVisitor visitor = new EatenLineVisitor();
        ghost.accept(visitor);
        ghost.switchEatenMode();
    }

    //겁먹은 상태 타이머(7초)가 종료되었을 때 원래 모드로 복귀
    @Override
    public void timerFrightenedModeOver() {
        ghost.switchChaseModeOrScatterMode();
    }

    //이 상태에서, 목표 위치는 유령 주변의 무작위 칸입니다.
    @Override
    public int[] getTargetPosition(){
        int[] position = new int[2];

        //팩맨을 피하는 복잡한 AI가 아니라, 그냥 자신의 현재 위치 주변의 '무작위' 지점을 목표로 설정
        boolean randomAxis = Utils.randomBool();
        position[0] = ghost.getxPos() + (randomAxis ? Utils.randomInt(-1,1) * 32 : 0);
        position[1] = ghost.getyPos() + (!randomAxis ? Utils.randomInt(-1,1) * 32 : 0);
        return position;
    }
}
