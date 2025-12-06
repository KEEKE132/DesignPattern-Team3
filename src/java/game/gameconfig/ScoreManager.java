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
     * 유령 콤보 로직 구현
     * 콤보 점수 계산 및 메세지 조직, UI 패널에 점수 변경 사실을 알림
     */
    public void notifyGhostEaten(int ghostsEatenCount, int baseScore) {
        // 콤보 점수 계산
        int comboScore = baseScore * (int) Math.pow(2, ghostsEatenCount - 1);

        // 메세지 조직
        String msg = "";
        if (ghostsEatenCount >= 4) {
            msg = "MONSTER! +" + comboScore;
        } else if (ghostsEatenCount > 1) {
            msg = "COMBO! +" + comboScore;
        } else {
            msg = "GHOST +" + comboScore;
        }
        this.addScore(comboScore, msg); // 점수 추가
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
