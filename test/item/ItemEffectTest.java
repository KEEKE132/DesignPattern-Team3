package item;

import game.Game;
import game.entities.Pacman;
import game.entities.items.HasteItem;
import game.entities.items.Item;
import game.entities.items.scoreItems.Cherry;
import game.entities.items.scoreItems.Orange;
import game.entities.items.scoreItems.ScoreItem;
import game.entities.items.scoreItems.Strawberry;
import game.gameconfig.LevelManager;
import game.gameconfig.ScoreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("EatenLineVisitor 테스트")
public class ItemEffectTest {

    private Game game;

    @BeforeEach
    void setUp() {
        LevelManager levelManager = new LevelManager();
        ScoreManager scoreManager = new ScoreManager();

        game = new Game(levelManager, scoreManager);
    }

    @Test
    @DisplayName("Cherry 아이템을 먹으면 점수가 100 증가한다")
    void cherry_onEaten_shouldIncreaseScore() {

        // given
        ScoreItem cherry = new Cherry(100, 100);
        int initialScore = game.getScoreManager().getCurrentScore();

        // when
        cherry.onEaten(game);

        // then
        assertThat(game.getScoreManager().getCurrentScore()).isEqualTo(initialScore + cherry.getScoreValue());
    }

    @Test
    @DisplayName("Orange 아이템을 먹으면 점수가 200 증가한다")
    void orange_onEaten_shouldIncreaseScore() {

        // given
        ScoreItem orange = new Orange(100, 100);
        int initialScore = game.getScoreManager().getCurrentScore();

        // when
        orange.onEaten(game);

        // then
        assertThat(game.getScoreManager().getCurrentScore()).isEqualTo(initialScore + orange.getScoreValue());
    }

    @Test
    @DisplayName("Strawberry 아이템을 먹으면 점수가 300 증가한다")
    void strawberry_onEaten_shouldIncreaseScore() {

        // given
        ScoreItem strawberry = new Strawberry(100, 100);
        int initialScore = game.getScoreManager().getCurrentScore();

        // when
        strawberry.onEaten(game);

        // then
        assertThat(game.getScoreManager().getCurrentScore()).isEqualTo(initialScore + strawberry.getScoreValue());
    }


    @Test
    @DisplayName("HasteItem 을 먹으면 속도가 증가한다")
    void hasteItem_onEaten_shouldIncreaseSpeed() {

        // given
        Pacman pacman = Game.getPacman();
        Item haste = new HasteItem(100, 100);
        int initialSpeed = pacman.getSpd();

        // when
        haste.onEaten(game);

        // then
        assertThat(pacman.getSpd()).isGreaterThan(initialSpeed);
    }

    @Test
    @DisplayName("여러 아이템을 연속으로 먹으면 효과가 누적된다 (점수 + 속도)")
    void eatMultipleItems_effectsAccumulate() {

        // given
        Pacman pacman = Game.getPacman();
        ScoreItem cherry = new Cherry(100,100);
        Item haste = new HasteItem(100,100);
        int initialScore = game.getScoreManager().getCurrentScore();
        int initialSpeed = pacman.getSpd();

        // when
        cherry.onEaten(game);
        haste.onEaten(game);

        // then
        assertThat(game.getScoreManager().getCurrentScore()).isEqualTo(initialScore + cherry.getScoreValue());
        assertThat(pacman.getSpd()).isGreaterThan(initialSpeed);
    }

}
