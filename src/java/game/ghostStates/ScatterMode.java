package game.ghostStates;

import game.entities.ghosts.Ghost;

//'휴식(흩어지기)'을 취하는 구체적인 상태
public class ScatterMode extends GhostState{
    public ScatterMode(Ghost ghost) {
        super(ghost);
    }

    //슈퍼팩껌(SuperPacGum)이 먹혔을 때의 전환
    @Override
    public void superPacGumEaten() {
        ghost.switchFrightenedMode();
    }

    //휴식 모드 타이머(5초)가 종료되었을 때 추격 모드로 전환
    @Override
    public void timerModeOver() {
        ghost.switchChaseMode();
    }

    //이 상태에서, 목표 위치는 유령의 전략(Strategy)에 따라 달라집니다.
    @Override
    public int[] getTargetPosition() {
        return ghost.getStrategy().getScatterTargetPosition();
    }
}
