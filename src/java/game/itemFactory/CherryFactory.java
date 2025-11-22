package game.itemFactory;

import game.entities.items.Cherry;
import game.entities.items.Item;

public class CherryFactory extends AbstractItemFactory {
    @Override
    public Item createItem(int xPos, int yPos) {
        return new Cherry(xPos, yPos);
    }
}
