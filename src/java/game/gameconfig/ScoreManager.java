package game.gameconfig;

import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private int currentScore;               // 현재까지 누적된 총 점수 (UiPanel 대신에 ScoreManager에서 관리)
    private final List<ScoreObserver> observers = new ArrayList<>();

    public void addObserver(ScoreObserver obs) {
        observers.add(obs);
    }

    public ScoreManager() {
        this.currentScore = 0;
    }

    /**
     * 점수를 리셋하고 UI 패널에 점수 변경 사실을 알림.
     */
    public void resetScore() {
        this.currentScore = 0;
        notifyObservers(new ScoreEvent(0, null));
    }

    /**
     * 점수를 추가하고 UI 패널에 점수 변경 사실을 알림.
     */
    public void addScore(int score, String message) {
        this.currentScore += score;
        notifyObservers(new ScoreEvent(score, message));
    }

    /**
     * 현재 총 점수를 반환.
     */
    public int getCurrentScore() {
        return currentScore;
    }

    private void notifyObservers(ScoreEvent scoreEvent) {
        for (ScoreObserver observer : observers) {
            observer.onScoreChanged(currentScore, scoreEvent);
        }
    }
}
