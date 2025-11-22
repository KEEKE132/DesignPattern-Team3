package game.itemFactory;

import game.entities.items.scoreItems.Cherry;
import game.entities.items.scoreItems.ScoreItem;

public class CherryFactory extends AbstractItemFactory {
    @Override
    public ScoreItem createItem(int xPos, int yPos) {
        return new Cherry(xPos, yPos);
    }
}
