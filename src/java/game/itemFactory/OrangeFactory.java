package game.itemFactory;

import game.entities.items.scoreItems.Orange;
import game.entities.items.scoreItems.ScoreItem;

public class OrangeFactory extends ItemFactory {
    @Override
    public ScoreItem createItem(int xPos, int yPos) {
        return new Orange(xPos, yPos);
    }
}
