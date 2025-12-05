package game.gameStates;

import game.GameplayPanel;
import game.gameconfig.ScoreManager;
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
class GameOverStateTest {
    @Mock
    GameplayPanel mockPanel; // 가짜 패널 생성

    GameOverState gameOverState;
    KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        gameOverState = new GameOverState(mockPanel);
        keyHandler = new KeyHandler(mockPanel);
    }

    @Test
    @DisplayName("Enter 키를 누르면 게임이 재시작(PlayingState) 되어야 한다.")
    void restartGame() {
        keyHandler.k_enter.toggle(true); // Enter 키 누름

        gameOverState.input(keyHandler);

        verify(mockPanel).setState(any(PlayingState.class)); // 게임 재시작
    }

    @Test
    @DisplayName("Esc 키를 누르면 메인 메뉴(MainMenuState)로 돌아가야 한다.")
    void backToMenu() {
        keyHandler.k_escape.toggle(true); // Esc 키 누름

        gameOverState.input(keyHandler);

        verify(mockPanel).setState(any(MainMenuState.class)); // 메인 메뉴로 이동
    }

    @Test
    @DisplayName("GameOverState 벗어날 시(onExit) 게임이 리셋되어야 한다.")
    void resetGameOnExit() {
        // Playing으로 진입 전 초기화
        gameOverState.onExit();

        verify(mockPanel).resetGame(); // 데이터 초기화
    }
}