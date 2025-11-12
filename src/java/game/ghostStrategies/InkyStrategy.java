package game.ghostStrategies;

import game.Game;
import game.GameplayPanel;
import game.entities.ghosts.Ghost;
import game.utils.Utils;

//Inky(파란 유령)의 구체적인 전략
public class InkyStrategy implements IGhostStrategy{
    private Ghost otherGhost;
    public InkyStrategy(Ghost ghost) {
        this.otherGhost = ghost;
    }

    //Inky는 팩맨을 목표로 하기 위해 Blinky의 위치를 기반으로 합니다: Blinky의 위치와 팩맨 앞의 한 칸 사이의 벡터를 구하고,
    //이 벡터를 팩맨 앞의 한 칸 위치에 더하여 Inky의 목표를 얻습니다.
    //Blinky-팩맨앞-Inky 목표 순으로 일직선을 만듦
    @Override
    public int[] getChaseTargetPosition() {
        int[] position = new int[2];
        int[] pacmanFacingPosition = Utils.getPointDistanceDirection(Game.getPacman().getxPos(), Game.getPacman().getyPos(), 32d, Utils.directionConverter(Game.getPacman().getDirection()));
        double distanceOtherGhost = Utils.getDistance(pacmanFacingPosition[0], pacmanFacingPosition[1], otherGhost.getxPos(), otherGhost.getyPos());
        double directionOtherGhost = Utils.getDirection(otherGhost.getxPos(), otherGhost.getyPos(), pacmanFacingPosition[0], pacmanFacingPosition[1]);
        int[] blinkyVectorPosition = Utils.getPointDistanceDirection(pacmanFacingPosition[0], pacmanFacingPosition[1], distanceOtherGhost, directionOtherGhost);
        position[0] = blinkyVectorPosition[0];
        position[1] = blinkyVectorPosition[1];
        return position;
    }

    //휴식 시, Pinky는 우측 하단 칸을 목표로 합니다.
    @Override
    public int[] getScatterTargetPosition() {
        int[] position = new int[2];
        position[0] = GameplayPanel.width;
        position[1] = GameplayPanel.height;
        return position;
    }
}
