package game.ghostStates;

import game.entities.ghosts.Ghost;
import game.ghostVisitor.GhostVisitor;
import game.ghostVisitor.SuperPacgumLineVisitor;

//팩맨을 '추격'하는 구체적인 상태
public class ChaseMode extends GhostState{
    public ChaseMode(Ghost ghost) {
        super(ghost);
    }

    //슈퍼팩껌(SuperPacGum)이 먹혔을 때의 전환
    @Override
    public void superPacGumEaten() {
        GhostVisitor visitor = new SuperPacgumLineVisitor();
        ghost.accept(visitor);
        ghost.switchFrightenedMode();
    }

    //추격 모드 타이머(20초)가 종료되었을 때 휴식 모드로 전환
    @Override
    public void timerModeOver() {
        ghost.switchScatterMode();
    }

    //이 상태에서, 목표 위치는 유령의 전략(Strategy)에 따라 달라집니다.
    @Override
    public int[] getTargetPosition() {
        return ghost.getStrategy().getChaseTargetPosition();
    }
}
