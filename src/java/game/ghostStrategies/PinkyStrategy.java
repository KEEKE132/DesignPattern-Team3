package game.ghostStrategies;

import game.Game;
import game.utils.Utils;

//Pinky(분홍 유령)의 구체적인 전략
public class PinkyStrategy implements IGhostStrategy {
    //Pinky는 팩맨의 두 칸 앞을 목표로 합니다.
    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        //팩맨의 좌표에서 64픽셀 떨어진 지점을 목표로 합니다.
        int[] pacmanFacingPosition = Utils.getPointDistanceDirection(Game.getPacman().getxPos(), Game.getPacman().getyPos(), 64, Utils.directionConverter(Game.getPacman().getDirection()));
        position[0] = pacmanFacingPosition[0];
        position[1] = pacmanFacingPosition[1];
        return position;
    }

    //휴식 시, Pinky는 좌측 상단 칸을 목표로 합니다.
    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = 0;
        position[1] = 0;
        return position;
    }
}
