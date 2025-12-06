package game;

import game.gameconfig.ScoreEvent;
import game.gameconfig.ScoreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.*;

import java.awt.*;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("UIPanel 테스트")
public class UIPanelTest {

    private UIPanel panel;

    @Mock
    private ScoreManager mockScoreManager;

    @BeforeEach
    public void setup() {
        panel = new UIPanel(800, 600, mockScoreManager);
    }

    @Test
    @DisplayName("생성 시 ScoreManager에 자기 자신을 옵저버로 등록해야 한다")
    void testConstructorRegistersObserver() {
        // When
        // Then
        verify(mockScoreManager).addObserver(panel);
    }

    @Test
    @DisplayName("onScoreChanged 호출 시 점수 라벨 텍스트가 갱신되어야 한다")
    void testScoreLabelUpdate() {
        // Given
        int newScore = 1250;
        ScoreEvent event = new ScoreEvent(newScore, null); // 메시지 없음

        // When
        panel.onScoreChanged(newScore, event);

        // Then
        JLabel scoreLabel = getLabelField(panel, "scoreLabel");
        assertEquals("Score: 1250", scoreLabel.getText());
    }

    @Test
    @DisplayName("이벤트 수신 시 메시지 라벨이 갱신된다.")
    void testMessageUpdate() {
        // Given
        String msg = "TEST MESSAGE";
        ScoreEvent event = new ScoreEvent(0, msg);

        // When
        panel.onScoreChanged(0, event);

        // Then
        JLabel messageLabel = getLabelField(panel, "messageLabel");

        // 1. 텍스트 내용 확인
        assertEquals(msg, messageLabel.getText());

        // 2. 색상이 확인
        assertEquals(Color.YELLOW, messageLabel.getForeground());
    }

    @Test
    @DisplayName("GHOST가 포함된 이벤트 수신 시 메시지 라벨 텍스트와 색상이 변경되어야 한다")
    void testMessageLabelOfGhostUpdate() {
        // Given
        String msg = "GHOST EATEN +200";
        ScoreEvent event = new ScoreEvent(500, msg);

        // When
        panel.onScoreChanged(500, event);

        // Then
        JLabel messageLabel = getLabelField(panel, "messageLabel");

        // 1. 텍스트 내용 확인
        assertEquals(msg, messageLabel.getText());

        // 2. "GHOST"가 포함되었으므로 색상이 ORANGE로 변했는지 확인 (로직 검증)
        assertEquals(Color.ORANGE, messageLabel.getForeground());
    }

    @Test
    @DisplayName("SPEED가 포함된 이벤트 수신 시 메시지 라벨 텍스트와 색상이 변경되어야 한다")
    void testMessageLabelOfSpeedUpdate() {
        // Given
        String msg = "SPEED UP!";
        ScoreEvent event = new ScoreEvent(0, msg);

        // When
        panel.onScoreChanged(0, event);

        // Then
        JLabel messageLabel = getLabelField(panel, "messageLabel");

        // 1. 텍스트 내용 확인
        assertEquals(msg, messageLabel.getText());

        // 2. "SPEED"가 포함되었으므로 색상이 CYAN로 변했는지 확인 (로직 검증)
        assertEquals(Color.CYAN, messageLabel.getForeground());
    }

    // --- Reflection Helper Method ---
    // private 필드인 JLabel을 강제로 꺼내오는 메서드
    private JLabel getLabelField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true); // 자물쇠 따기
            return (JLabel) field.get(target);
        } catch (Exception e) {
            throw new RuntimeException("테스트 중 필드를 가져오지 못했습니다: " + fieldName, e);
        }
    }

}
