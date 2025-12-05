package game.gameconfig;

/**
 * 점수 변경 이벤트가 발생했을 때, 추가된 점수와 UI에 표시할 메시지 정보를 담는 레코드입니다.
 * Observer 패턴을 통해 ScoreManager에서 구독자(UIPanel)로 전달됩니다.
 *
 * @param scoreAdded 이번 이벤트로 인해 추가된 점수의 양
 * @param message    UI 화면에 잠시 띄울 알림 텍스트 (예: "COMBO! +400", "SPEED UP!!")
 */
public record ScoreEvent(int scoreAdded, String message) {
}