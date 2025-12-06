package game.gameconfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScoreManager 테스트")
public class ScoreManagerTest {

    private ScoreManager scoreManager;

    @Mock
    private ScoreObserver mockObserver;

    @BeforeEach
    void setUp() {
        scoreManager = new ScoreManager();
        scoreManager.addObserver(mockObserver); // Mock 옵저버 등록
    }

    @Test
    @DisplayName("스코어 매니저 초기화 점수는 0이다.")
    void initializeState() {
        assertEquals(0, scoreManager.getCurrentScore());
    }

    @Test
    @DisplayName("점수를 추가하면 누적 점수가 증가하고 옵저버에게 알림이 가야 한다")
    void testAddScore() {
        // Given
        int addAmount = 100;
        String message = "Item Eaten";

        // When
        scoreManager.addScore(addAmount, message);

        // Then
        // 1. 내부 데이터 검증
        assertEquals(100, scoreManager.getCurrentScore());

        // 2. 알림 발송 검증 (점수와 메시지가 맞게 전달되었는지 확인)
        // argThat을 사용하여 ScoreEvent 객체 내부의 메시지까지 검증
        verify(mockObserver).onScoreChanged(
                eq(100),
                argThat(event -> event.message().equals(message))
        );
    }

    @Test
    @DisplayName("점수를 리셋하면 0점이 되고 초기화 알림이 가야 한다")
    void testResetScore() {
        // Given (점수를 미리 좀 올려둠)
        scoreManager.addScore(500, "Setup");

        // When
        scoreManager.resetScore();

        // Then
        assertEquals(0, scoreManager.getCurrentScore());
        // 리셋 시 점수는 0, 메시지는 null(또는 코드에 따라 다름)로 알림이 가는지 확인
        verify(mockObserver).onScoreChanged(eq(0), argThat(event -> event.message() == null));
    }
}
