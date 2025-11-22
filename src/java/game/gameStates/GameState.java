package game.gameStates;

import game.utils.KeyHandler;

import java.awt.*;

public interface GameState {
    /**
     * 게임의 논리적인 상태를 매 프레임마다 업데이트합니다.
     * (예: 팩맨 이동, 유령 AI 계산, 충돌 감지, 타이머 감소 등)
     */
    void update();

    /**
     * 게임의 시각적인 요소를 화면에 그립니다.
     * (예: 배경 이미지, 캐릭터 스프라이트, 점수 텍스트, "GAME OVER" 메시지 등)
     * @param g 그래픽 컨텍스트 (그림을 그리는 도구)
     */
    void render(Graphics2D g);

    /**
     * 사용자의 키보드 입력을 처리합니다.
     * 각 상태마다 키 입력에 대한 반응이 다를 수 있습니다.
     * (예: 메뉴에서는 Enter가 '시작', 플레이 중에는 '일시정지', 게임 오버에서는 '재시작')
     * @param k 키 입력 상태를 관리하는 핸들러
     */
    void input(KeyHandler k);

    /**
     * 이 상태로 전환(진입)될 때 한 번 호출되는 초기화 메서드
     * (예: 게임 시작 시 점수 초기화, 변수 리셋 등)
     * 생성자 외에 상태 전환 시점에 수행해야 할 작업이 있을 때 유용합니다.
     */
    void onEnter();

    /**
     * 이 상태에서 빠져나갈 때(다른 상태로 전환될 때) 한 번 호출되는 정리 메서드
     * (예: 사용하던 리소스 해제 등)
     */
    void onExit();
}
