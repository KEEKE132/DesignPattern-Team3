package game.entities.ghosts;

import game.entities.SuperPacGum;
import game.gameconfig.LevelConfig;
import game.ghostStates.FrightenedMode;
import game.ghostStates.GhostState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Ghost의 상태 변경 테스트")
public class GhostTest {

    private Ghost ghost;

    @Mock
    private LevelConfig mockLevelConfig;

    @Mock
    private GhostState mockState;

    @BeforeEach
    void setUp() {
        ghost = new Blinky(1, 2, mockLevelConfig);
        ghost.setState(mockState);
    }

    @Test
    @DisplayName("슈퍼 팩껌을 먹으면 고스트 상태 객체의 superPacGumEaten()이 호출되어야 한다")
    void testSuperPacGumEaten() {
        // Given

        // When
        ghost.updateSuperPacGumEaten(new SuperPacGum(1, 1));

        // Then
        verify(mockState, times(1)).superPacGumEaten();
    }

    @Test
    @DisplayName("다른 유령이 충돌한 이벤트는 무시해야 한다")
    void testCollisionWithOtherGhost() {
        // Given
        Ghost otherGhost = mock(Ghost.class); // 다른 유령 객체

        // When
        // 충돌한 유령(gh)으로 otherGhost를 전달
        ghost.updateGhostCollision(otherGhost);

        // Then
        // 먹힌 상태로 전환(eaten)되지 않아야 함
        verify(mockState, never()).eaten();
    }

    @Test
    @DisplayName("겁먹은 상태(FrightenedMode)가 아닐 때는 충돌해도 먹히지 않아야 한다")
    void testCollisionWhenNotFrightened() {
        // Given

        // When
        // 나 자신(ghost)이 충돌했다고 알림
        ghost.updateGhostCollision(ghost);

        // Then
        // FrightenedMode가 아니므로 eaten()이 호출되면 안 됨
        verify(mockState, never()).eaten();
    }

    @Test
    @DisplayName("겁먹은 상태(FrightenedMode)에서 충돌하면 먹힌 상태(eaten)로 전환되어야 한다")
    void testCollisionWhenFrightened() {
        // Given

        // FrightenedMode 타입의 Mock 객체 생성
        // (instanceof FrightenedMode 체크를 통과하기 위해 구체적인 클래스로 mock 생성)
        FrightenedMode mockFrightenedState = mock(FrightenedMode.class);
        ghost.setState(mockFrightenedState);

        // When
        ghost.updateGhostCollision(ghost);

        // Then
        verify(mockFrightenedState, times(1)).eaten();
    }

    @Test
    @DisplayName("일반 팩껌이나 아이템 획득 이벤트는 고스트 상태에 영향을 주지 않아야 한다")
    void testIgnoredEvents() {
        // Given (setUp 상태)

        // When
        ghost.updatePacGumEaten(null); // 인자가 안 쓰이니 null도 OK
        ghost.updateItemEaten(null);

        // Then
        // mockState에 아무런 메서드도 호출되지 않았음을 검증
        verifyNoInteractions(mockState);
    }
}
