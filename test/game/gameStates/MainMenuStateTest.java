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
class MainMenuStateTest {
    @Mock
    GameplayPanel mockPanel;

    MainMenuState mainMenuState;
    KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        mainMenuState = new MainMenuState(mockPanel);
        keyHandler = new KeyHandler(mockPanel);
    }

    @Test
    @DisplayName("Enter 키를 누르면, 게임이 시작(PlayingState)되어야 한다.")
    void startGame() {
        keyHandler.k_enter.toggle(true); // Enter 키 누름

        mainMenuState.input(keyHandler);

        verify(mockPanel).setState(any(PlayingState.class)); // 게임 시작
    }

    @Test
    @DisplayName("Esc 키를 누르면, 게임이 종료(System.exit(0)되어야 한다.")
    void quitGame() {
        keyHandler.k_escape.toggle(true); // Esc 키 누름

        mainMenuState.input(keyHandler);

        verify(mockPanel).quitGame(); // 게임 종료
    }

    @Test
    @DisplayName("MainMenuState 진입 시(onEnter) 게임이 리셋되어야 한다.")
    void resetGameOnEnter() {
        mainMenuState.onEnter();

        verify(mockPanel).resetGame(); // 데이터 초기화
    }
}