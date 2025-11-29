package game.entities.items.scoreItems;

import game.Game;
import game.entities.items.Item;

public class ScoreItem extends Item {
    private final int scoreValue;

    public ScoreItem(int xPos, int yPos, int score, String imagePath) {
        super(xPos, yPos, imagePath);
        this.scoreValue = score;
    }

    @Override
    public boolean isRequiredToClear() {
        return false;
    }

    @Override
    public void onEaten(Game game) {
        // Game 클래스에 점수를 추가하라고 요청
        game.getScoreManager().addScore(scoreValue, null);
    }
}
