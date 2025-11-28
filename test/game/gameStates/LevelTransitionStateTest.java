package game.gameStates;

import game.GameplayPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LevelTransitionStateTest {
    static final int TRANSITION_TIME = 120; // 2초 (60fps * 2)

    @Mock
    GameplayPanel mockPanel;

    LevelTransitionState levelTransitionState;

    @BeforeEach
    void setUp() {
        levelTransitionState = new LevelTransitionState(mockPanel);
    }

    @Test
    @DisplayName("시간(120프레임)이 지나면 다음 레벨(PlayingState)이 시작되어야 한다.")
    void update() {
        // 시간을 빨리 감기 (update를 120번 호출)
        for (int i = 0; i < TRANSITION_TIME; i++) {
            levelTransitionState.update();
        }

        verify(mockPanel).setState(any(PlayingState.class)); // 다음 레벨 시작
    }

    @Test
    @DisplayName("시간이 아직 안 지났으면 화면이 전환되지 않아야 한다.")
    void waitTime() {
        // 119번까지만 반복 (아직 1프레임 모자람)
        for (int i = 0; i < TRANSITION_TIME - 1; i++) {
            levelTransitionState.update();
        }

        // setState가 단 한 번도 호출되지 않았어야 함 (never())
        verify(mockPanel, never()).setState(any());
    }
}