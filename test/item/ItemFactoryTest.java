package item;

import game.entities.items.HasteItem;
import game.entities.items.Item;
import game.entities.items.scoreItems.Cherry;
import game.entities.items.scoreItems.Orange;
import game.entities.items.scoreItems.Strawberry;
import game.itemFactory.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ItemFactoryTest {

    @Test
    @DisplayName("팩토리 메서드가 Cherry를 생성한다")
    void create_Cherry() {

        // given
        ItemFactory factory = new CherryFactory();

        // when
        Item cherry = factory.createItem(100, 100);

        // then
        assertThat(cherry).isInstanceOf(Cherry.class);
    }

    @Test
    @DisplayName("팩토리 메서드가 Orange를 생성한다")
    void create_Orange() {

        // given
        ItemFactory factory = new OrangeFactory();

        // when
        Item orange = factory.createItem(100, 100);

        // then
        assertThat(orange).isInstanceOf(Orange.class);
    }

    @Test
    @DisplayName("팩토리 메서드가 Strawberry를 생성한다")
    void create_Strawberry() {

        // given
        ItemFactory factory = new StrawberryFactory();

        // when
        Item strawberry = factory.createItem(100, 100);

        // then
        assertThat(strawberry).isInstanceOf(Strawberry.class);
    }

    @Test
    @DisplayName("팩토리 메서드가 HasteItem를 생성한다")
    void create_HasteItem() {

        // given
        ItemFactory factory = new HasteFactory();

        // when
        Item hasteItem = factory.createItem(100, 100);

        // then
        assertThat(hasteItem).isInstanceOf(HasteItem.class);
    }

    @Test
    @DisplayName("ScoreItem은 requiredToClear=true, HasteItem은 false")
    void requiredToClear_flag_test() {

        assertThat(new CherryFactory().createItem(0,0).isRequiredToClear()).isTrue();
        assertThat(new OrangeFactory().createItem(0,0).isRequiredToClear()).isTrue();
        assertThat(new StrawberryFactory().createItem(0,0).isRequiredToClear()).isTrue();

        assertThat(new HasteFactory().createItem(0,0).isRequiredToClear()).isFalse();

    }

    @Test
    @DisplayName("ScoreItem이 올바른 점수 값을 가진다")
    void scoreItem_has_correct_scoreValue() {
        Item cherry = new CherryFactory().createItem(0,0);
        Item orange = new OrangeFactory().createItem(0,0);
        Item strawberry = new StrawberryFactory().createItem(0,0);

        assertThat(((Cherry) cherry).getScoreValue()).isEqualTo(100);
        assertThat(((Orange) orange).getScoreValue()).isEqualTo(200);
        assertThat(((Strawberry) strawberry).getScoreValue()).isEqualTo(300);
    }

}
