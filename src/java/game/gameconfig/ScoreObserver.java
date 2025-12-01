package game.gameconfig;

public interface ScoreObserver {
    void onScoreChanged(int currentScore, ScoreEvent scoreEvent);
}
