package game.gameStates;

import game.Game;
import game.GameplayPanel;
import game.gameconfig.LevelManager;
import game.utils.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayingStateTest {

    @Mock
    GameplayPanel mockPanel;
    @Mock
    LevelManager mockLevelManager;
    @Mock
    Game mockGame; // 가짜 게임 객체

    KeyHandler keyHandler;

    // 테스트용으로 PlayingState의 자식을 사용
    TestablePlayingState playingState;

    /**
     * 원본 PlayingState는 내부에서 'new Game()'을 직접 호출하여 테스트가 불가능
     * 이를 해결하기 위해, PlayingState를 상속받아 'createGameInstance' 메서드만 재정의
     * 실제 게임 로직(이미지 로딩 등)은 피하고, 'mockGame'을 끼워 넣기 위함
     */
    class TestablePlayingState extends PlayingState {
        public TestablePlayingState(GameplayPanel gameplayPanel) {
            super(gameplayPanel);
        }

        // createGameInstance를 오버라이딩하여, 가짜(Mock)를 반환
        @Override
        protected Game createGame() {
            return mockGame;
        }
    }

    @BeforeEach
    void setUp() {
        playingState = new TestablePlayingState(mockPanel);
        keyHandler = new KeyHandler(mockPanel);

        // onEnter() 호출 -> 내부적으로 재정의한 createGameInstance()가 실행됨
        // 결과적으로 playingState 내부에 진짜 Game 대신 'mockGame'이 들어가게 됨
        playingState.onEnter();

        // lenient(): 이 설정이 사용되지 않는 테스트(예: pauseGame)에서도 에러를 내지 말라는 뜻.
        lenient().when(mockPanel.getLevelManager()).thenReturn(mockLevelManager);
    }

    @Test
    @DisplayName("P 키를 누르면 일시정지(PausedState)로 전환되어야 한다.")
    void pauseGame() {
        keyHandler.k_p.toggle(true); // P 키 누름

        playingState.input(keyHandler);

        verify(mockPanel).setState(any(PausedState.class)); // 일시 정지
    }

    @Test
    @DisplayName("게임 오버(isGameOver)되면 GameOverState로 전환되어야 한다.")
    void gameOver() {
        when(mockGame.isGameOver()).thenReturn(true); // 게임 오버

        playingState.update();

        verify(mockPanel).setState(any(GameOverState.class)); // 게임 오버 상태로 이동
    }

    @Test
    @DisplayName("레벨 클리어 시, 다음 레벨이 있으면 레벨 전환(LevelTransitionState)으로 이동한다.")
    void levelClear_NextLevel() {
        when(mockGame.isLevelCleared()).thenReturn(true);       // 클리어 함
        when(mockLevelManager.hasNextLevel()).thenReturn(true); // 다음 레벨 있음

        playingState.update();

        verify(mockLevelManager).moveToNextLevel(); // 레벨 이동 메서드 호출 확인
        verify(mockPanel).setState(any(LevelTransitionState.class)); // 다음 레벨로 전환
    }

    @Test
    @DisplayName("레벨 클리어 시, 다음 레벨이 없으면 승리(GameWonState) 화면으로 이동한다.")
    void levelClear_GameWon() {
        when(mockGame.isLevelCleared()).thenReturn(true);        // 클리어 함
        when(mockLevelManager.hasNextLevel()).thenReturn(false); // 다음 레벨 없음 (마지막 판)

        playingState.update();

        verify(mockPanel).setState(any(GameWonState.class)); // 게임 승리 상태로 이동
    }
}