package game.itemFactory;

import game.entities.items.HasteItem;
import game.entities.items.Item;

public class HasteFactory extends AbstractItemFactory {
    @Override
    public Item createItem(int xPos, int yPos) {
        return new HasteItem(xPos, yPos);
    }
}
