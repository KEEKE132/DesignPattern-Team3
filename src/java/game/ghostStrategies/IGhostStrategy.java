package game.ghostStrategies;

//각기 다른 유령의 전략(Strategy)을 기술(설명)하기 위한 인터페이스 (이 비디오가 잘 설명해 줍니다: https://www.youtube.com/watch?v=ataGotQ7ir8)
public interface IGhostStrategy {
    int[] getChaseTargetPosition(); //추격 모드(ChaseMode)일 때, 팩맨을 잡기 위해 어디로 가야 하는지 그 목표 좌표 [x, y]를 반환
    int[] getScatterTargetPosition(); //흩어지기 모드(ScatterMode)일 때, 팩맨과 상관없이 순찰해야 하는 자신만의 "구역" 좌표 [x, y]를 반환
}
