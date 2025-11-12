package game.ghostStates;

import game.entities.ghosts.Ghost;
import game.utils.Utils;
import game.utils.WallCollisionDetector;

//유령의 여러 가지 상태를 기술(설명)하기 위한 추상 클래스
public abstract class GhostState {
    protected Ghost ghost;

    public GhostState(Ghost ghost) {
        this.ghost = ghost;
    }

    //한 상태에서 다른 상태로의 여러 가지 가능한 전환(이벤트)
    //이 메서드들은 Ghost가 state 객체에게 "이런 이벤트가 발생했어!"라고 알려주는 신호(이벤트 훅)
    //빈 메서드를 제공함으로써, 각 구체적인 상태 클래스(예: ChaseMode)는 자신이 관심 있는 이벤트만 선택적으로 오버라이딩
    public void superPacGumEaten() {}
    public void timerModeOver() {}
    public void timerFrightenedModeOver() {}
    public void eaten() {}
    public void outsideHouse() {}
    public void insideHouse() {}


    //Ghost가 '어디로' 가야 하는지를 결정
    public int[] getTargetPosition(){
        return new int[2];
    } //유령이 목표로 할 지점(좌표)을 반환합니다

    //유령이 취할 다음 방향을 계산하는 메서드
    //getTargetPosition()에서 정해진 '목표'까지 '어떻게' 갈지, 그 '다음 한 칸'을 결정하는 로직
    //벽이 막혀있지 않은 후보 방향들 중, '목표 지점'까지의 직선 거리를 가장 짧게 만드는 '최적의' 방향 하나를 선택
    public void computeNextDir() {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!ghost.onTheGrid()) return; //유령은 게임 영역의 "칸" 위에 있어야 합니다 (그리드 정렬).
        if (!ghost.onGameplayWindow()) return; //유령은 게임 영역 내에 있어야 합니다.

        double minDist = Double.MAX_VALUE; //다음 방향에 따른 유령과 목표물 사이의 현재 최소 거리

        //만약 유령이 현재 왼쪽으로 가고 있고 왼쪽에 벽이 없다면...
        if (ghost.getxSpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, -ghost.getSpd(), 0)) {
            //유령이 왼쪽으로 갔을 때의 잠재적 위치와 목표 위치 사이의 거리를 봅니다.
            double distance = Utils.getDistance(ghost.getxPos() - ghost.getSpd(), ghost.getyPos(), getTargetPosition()[0], getTargetPosition()[1]);

            //만약 이 거리가 현재 최소 거리보다 작다면, 유령이 왼쪽으로 가도록 설정하고 최소 거리를 업데이트합니다.
            if (distance < minDist) {
                new_xSpd = -ghost.getSpd();
                new_ySpd = 0;
                minDist = distance;
            }
        }

        //오른쪽 방향으로도 동일하게 테스트합니다.
        if (ghost.getxSpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, ghost.getSpd(), 0)) {
            double distance = Utils.getDistance(ghost.getxPos() + ghost.getSpd(), ghost.getyPos(),  getTargetPosition()[0], getTargetPosition()[1]);
            if (distance < minDist) {
                new_xSpd = ghost.getSpd();
                new_ySpd = 0;
                minDist = distance;
            }
        }

        //위 방향으로도 동일하게 테스트합니다.
        if (ghost.getySpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, -ghost.getSpd())) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() - ghost.getSpd(), getTargetPosition()[0], getTargetPosition()[1]);
            if (distance < minDist) {
                new_xSpd = 0;
                new_ySpd = -ghost.getSpd();
                minDist = distance;
            }
        }

        //아래 방향으로도 동일하게 테스트합니다.
        if (ghost.getySpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, ghost.getSpd())) {
            double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() + ghost.getSpd(), getTargetPosition()[0], getTargetPosition()[1]);
            if (distance < minDist) {
                new_xSpd = 0;
                new_ySpd = ghost.getSpd();
                minDist = distance;
            }
        }

        if (new_xSpd == 0 && new_ySpd == 0) return;

        //모든 경우를 테스트한 후, 유령의 방향을 변경합니다 (대각선으로 갈 수 없도록 확인).
        if (Math.abs(new_xSpd) != Math.abs(new_ySpd)) {
            ghost.setxSpd(new_xSpd);
            ghost.setySpd(new_ySpd);
        } else {
            if (new_xSpd != 0) {
                ghost.setxSpd(0);
                ghost.setxSpd(new_ySpd);
            }else{
                ghost.setxSpd(new_xSpd);
                ghost.setySpd(0);
            }
        }
    }
}
