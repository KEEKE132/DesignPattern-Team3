package game.gameStates;

import game.GameplayPanel;
import game.utils.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PausedStateTest {
    @Mock
    GameplayPanel mockPanel;

    @Mock
    PlayingState mockPreviousState; // 가짜 이전 상태

    PausedState pausedState;
    KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        pausedState = new PausedState(mockPanel, mockPreviousState);
        keyHandler = new KeyHandler(mockPanel);
    }

    @Test
    @DisplayName("P 키를 누르면, 이전 상태(PlayingState)로 복구되어야 한다.")
    void resumeGame() {
        keyHandler.k_p.toggle(true); // P 키 누름

        pausedState.input(keyHandler);

        verify(mockPanel).setState(mockPreviousState); // 게임 재개
    }

    @Test
    @DisplayName("Esc 키를 누르면, 메인 메뉴(MainMenuState)로 돌아가야 한다.")
    void backToMenu() {
        keyHandler.k_escape.toggle(true); // Esc 키 누름

        pausedState.input(keyHandler);

        verify(mockPanel).setState((any(MainMenuState.class))); // 메인 메뉴로 이동
    }
}