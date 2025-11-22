package game.itemFactory;

import game.entities.items.Item;
import game.entities.items.scoreItems.Orange;

public class OrangeFactory extends AbstractItemFactory {
    @Override
    public Item createItem(int xPos, int yPos) {
        return new Orange(xPos, yPos);
    }
}
