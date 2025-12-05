package ghostLineVisitor;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;
import game.entities.ghosts.Pinky;
import game.gameconfig.LevelConfig;
import game.ghostVisitor.HouseLineVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("HouseLineVisitor 테스트")
class HouseLineVisitorTest {

    private HouseLineVisitor visitor;

    @Mock
    private LevelConfig mockLevelConfig;

    @BeforeEach
    void setUp() {
        visitor = new HouseLineVisitor();
    }

    @Test
    @DisplayName("Blinky에게 방문하면 'Fully recovered!\\nTime for revenge!' 대사를 설정한다")
    void testVisitBlinky() {
        // given
        Blinky blinky = spy(new Blinky(100, 100, mockLevelConfig));

        // when
        visitor.visit(blinky);

        // then
        verify(blinky).setDialogue("Fully recovered!\nTime for revenge!");
    }

    @Test
    @DisplayName("Pinky에게 방문하면 'Recovered!\\nI won't miss next time!' 대사를 설정한다")
    void testVisitPinky() {
        // given
        Pinky pinky = spy(new Pinky(100, 100, mockLevelConfig));

        // when
        visitor.visit(pinky);

        // then
        verify(pinky).setDialogue("Recovered!\nI won't miss next time!");
    }

    @Test
    @DisplayName("Inky에게 방문하면 'Let me think of a new strategy...' 대사를 설정한다")
    void testVisitInky() {
        // given
        Inky inky = spy(new Inky(100, 100, mockLevelConfig));

        // when
        visitor.visit(inky);

        // then
        verify(inky).setDialogue("Let me think of a new strategy...");
    }

    @Test
    @DisplayName("Clyde에게 방문하면 'Phew! I'm feeling better now!' 대사를 설정한다")
    void testVisitClyde() {
        // given
        Clyde clyde = spy(new Clyde(100, 100, mockLevelConfig));

        // when
        visitor.visit(clyde);

        // then
        verify(clyde).setDialogue("Phew! I'm feeling better now!");
    }

    @Test
    @DisplayName("일반 Ghost에게 방문하면 'I'm back and ready!' 대사를 설정한다")
    void testVisitGhost() {
        // given
        Ghost ghost = mock(Ghost.class);

        // when
        visitor.visit(ghost);

        // then
        verify(ghost).setDialogue("I'm back and ready!");
    }

    @Test
    @DisplayName("Blinky의 accept 메서드를 통한 더블 디스패치가 정상 동작한다")
    void testDoubleDispatchWithBlinky() {
        // given
        Blinky blinky = spy(new Blinky(100, 100, mockLevelConfig));

        // when
        blinky.accept(visitor);

        // then
        verify(blinky).setDialogue("Fully recovered!\nTime for revenge!");
    }

    @Test
    @DisplayName("Pinky의 accept 메서드를 통한 더블 디스패치가 정상 동작한다")
    void testDoubleDispatchWithPinky() {
        // given
        Pinky pinky = spy(new Pinky(100, 100, mockLevelConfig));

        // when
        pinky.accept(visitor);

        // then
        verify(pinky).setDialogue("Recovered!\nI won't miss next time!");
    }

    @Test
    @DisplayName("Inky의 accept 메서드를 통한 더블 디스패치가 정상 동작한다")
    void testDoubleDispatchWithInky() {
        // given
        Inky inky = spy(new Inky(100, 100, mockLevelConfig));

        // when
        inky.accept(visitor);

        // then
        verify(inky).setDialogue("Let me think of a new strategy...");
    }

    @Test
    @DisplayName("Clyde의 accept 메서드를 통한 더블 디스패치가 정상 동작한다")
    void testDoubleDispatchWithClyde() {
        // given
        Clyde clyde = spy(new Clyde(100, 100, mockLevelConfig));

        // when
        clyde.accept(visitor);

        // then
        verify(clyde).setDialogue("Phew! I'm feeling better now!");
    }

    @Test
    @DisplayName("여러 유령들이 차례로 집으로 돌아왔을 때 각각 다른 대사를 설정한다")
    void testMultipleGhostsReturningHome() {
        // given
        Blinky blinky = spy(new Blinky(100, 100, mockLevelConfig));
        Pinky pinky = spy(new Pinky(200, 200, mockLevelConfig));
        Inky inky = spy(new Inky(300, 300, mockLevelConfig));
        Clyde clyde = spy(new Clyde(400, 400, mockLevelConfig));

        // when
        visitor.visit(blinky);
        visitor.visit(pinky);
        visitor.visit(inky);
        visitor.visit(clyde);

        // then
        verify(blinky).setDialogue("Fully recovered!\nTime for revenge!");
        verify(pinky).setDialogue("Recovered!\nI won't miss next time!");
        verify(inky).setDialogue("Let me think of a new strategy...");
        verify(clyde).setDialogue("Phew! I'm feeling better now!");
    }
}

