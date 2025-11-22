package game.itemFactory;

import game.entities.items.scoreItems.ScoreItem;
import game.entities.items.scoreItems.Strawberry;

public class StrawberryFactory extends AbstractItemFactory {
    @Override
    public ScoreItem createItem(int xPos, int yPos) {
        return new Strawberry(xPos, yPos);
    }
}
