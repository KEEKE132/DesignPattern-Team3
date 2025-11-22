package game.itemFactory;

import game.entities.items.Item;
import game.entities.items.scoreItems.Strawberry;

public class StrawberryFactory extends AbstractItemFactory {
    @Override
    public Item createItem(int xPos, int yPos) {
        return new Strawberry(xPos, yPos);
    }
}
