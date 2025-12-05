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
class GameWonStateTest {
    @Mock
    GameplayPanel mockPanel;

    GameWonState gameWonState;
    KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        gameWonState = new GameWonState(mockPanel);
        keyHandler = new KeyHandler(mockPanel);
    }

    @Test
    @DisplayName("Esc 키를 누르면, 메인 메뉴(MainMenuState)로 돌아가야 한다.")
    void backToMenu() {
        keyHandler.k_escape.toggle(true); // Enter 키 누름

        gameWonState.input(keyHandler);

        verify(mockPanel).setState(any(MainMenuState.class)); // 메인 메뉴로 이동
    }
}