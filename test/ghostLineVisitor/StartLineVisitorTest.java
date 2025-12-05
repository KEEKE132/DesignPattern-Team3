package ghostLineVisitor;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;
import game.entities.ghosts.Pinky;
import game.gameconfig.LevelConfig;
import game.ghostVisitor.StartLineVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StartLineVisitor 테스트")
class StartLineVisitorTest {

    private StartLineVisitor visitor;

    @Mock
    private LevelConfig mockLevelConfig;

    @BeforeEach
    void setUp() {
        visitor = new StartLineVisitor();
    }

    @Test
    @DisplayName("Blinky에게 방문하면 'Hi I`m Blinky!' 대사를 설정한다")
    void testVisitBlinky() {
        // given
        Blinky blinky = spy(new Blinky(100, 100, mockLevelConfig));

        // when
        visitor.visit(blinky);

        // then
        verify(blinky).setDialogue("Hi I`m Blinky!");
    }

    @Test
    @DisplayName("Pinky에게 방문하면 'Hi I`m Pinky!' 대사를 설정한다")
    void testVisitPinky() {
        // given
        Pinky pinky = spy(new Pinky(100, 100, mockLevelConfig));

        // when
        visitor.visit(pinky);

        // then
        verify(pinky).setDialogue("Hi I`m Pinky!");
    }

    @Test
    @DisplayName("Inky에게 방문하면 'Hi I`m Inky!' 대사를 설정한다")
    void testVisitInky() {
        // given
        Inky inky = spy(new Inky(100, 100, mockLevelConfig));

        // when
        visitor.visit(inky);

        // then
        verify(inky).setDialogue("Hi I`m Inky!");
    }

    @Test
    @DisplayName("Clyde에게 방문하면 'Hi I`m Clyde!' 대사를 설정한다")
    void testVisitClyde() {
        // given
        Clyde clyde = spy(new Clyde(100, 100, mockLevelConfig));

        // when
        visitor.visit(clyde);

        // then
        verify(clyde).setDialogue("Hi I`m Clyde!");
    }

    @Test
    @DisplayName("일반 Ghost에게 방문하면 'Hi I`m Ghost!' 대사를 설정한다")
    void testVisitGhost() {
        // given
        Ghost ghost = mock(Ghost.class);

        // when
        visitor.visit(ghost);

        // then
        verify(ghost).setDialogue("Hi I`m Ghost!");
    }

    @Test
    @DisplayName("Blinky의 accept 메서드를 통한 더블 디스패치가 정상 동작한다")
    void testDoubleDispatchWithBlinky() {
        // given
        Blinky blinky = spy(new Blinky(100, 100, mockLevelConfig));

        // when
        blinky.accept(visitor);

        // then
        verify(blinky).setDialogue("Hi I`m Blinky!");
    }

    @Test
    @DisplayName("Pinky의 accept 메서드를 통한 더블 디스패치가 정상 동작한다")
    void testDoubleDispatchWithPinky() {
        // given
        Pinky pinky = spy(new Pinky(100, 100, mockLevelConfig));

        // when
        pinky.accept(visitor);

        // then
        verify(pinky).setDialogue("Hi I`m Pinky!");
    }

    @Test
    @DisplayName("Inky의 accept 메서드를 통한 더블 디스패치가 정상 동작한다")
    void testDoubleDispatchWithInky() {
        // given
        Inky inky = spy(new Inky(100, 100, mockLevelConfig));

        // when
        inky.accept(visitor);

        // then
        verify(inky).setDialogue("Hi I`m Inky!");
    }

    @Test
    @DisplayName("Clyde의 accept 메서드를 통한 더블 디스패치가 정상 동작한다")
    void testDoubleDispatchWithClyde() {
        // given
        Clyde clyde = spy(new Clyde(100, 100, mockLevelConfig));

        // when
        clyde.accept(visitor);

        // then
        verify(clyde).setDialogue("Hi I`m Clyde!");
    }

    @Test
    @DisplayName("모든 유령 타입이 StartLineVisitor를 올바르게 처리한다")
    void testAllGhostTypesWithStartLineVisitor() {
        // given
        Blinky blinky = spy(new Blinky(100, 100, mockLevelConfig));
        Pinky pinky = spy(new Pinky(200, 200, mockLevelConfig));
        Inky inky = spy(new Inky(300, 300, mockLevelConfig));
        Clyde clyde = spy(new Clyde(400, 400, mockLevelConfig));

        // when
        visitor.visit(pinky);
        visitor.visit(inky);
        visitor.visit(clyde);
        visitor.visit(blinky);

        // then
        verify(blinky).setDialogue("Hi I`m Blinky!");
        verify(pinky).setDialogue("Hi I`m Pinky!");
        verify(inky).setDialogue("Hi I`m Inky!");
        verify(clyde).setDialogue("Hi I`m Clyde!");
    }
}
