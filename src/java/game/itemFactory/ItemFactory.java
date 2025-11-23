package game.itemFactory;

import game.entities.items.Item;

public abstract class ItemFactory {
    public abstract Item createItem(int xPos, int yPos);
}
