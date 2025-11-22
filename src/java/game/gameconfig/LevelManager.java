package game.gameconfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 여러 개의 LevelConfig(레벨 설정)와 게임의 전역 상태(점수)를 관리하는 매니저 클래스.
 */
public class LevelManager {
    private final List<LevelConfig> levels; //레벨 설정 목록
    private int currentLevelIndex;          // 현재 진행 중인 레벨 인덱스
    private int currentScore;               // 현재까지 누적된 총 점수 (UiPanel 대신에 LevelManager에서 관리)

    public LevelManager() {
        levels = new ArrayList<>();
        currentLevelIndex = 0;
        currentScore = 0;

        loadLevels(); // 레벨 설정 로드
    }

    /**
     * 게임에 사용할 레벨들을 정의하고 리스트에 추가
     */
    private void loadLevels() {
        // Level 1: 기본 난이도
        levels.add(
                LevelConfig.builder()
                        .levelMap("level/temp1.csv")
                        .speeds(2, 4)
                        .build()
        );

        // Level 2
        levels.add(
                LevelConfig.builder()
                .levelMap("level/temp2.csv")
                .speeds(2, 4)
                .seconds(5, 25, 4, 2)
                .scores(15, 150, 750)
                .build()
        );

        // Level 3
        levels.add(
                LevelConfig.builder()
                        .levelMap("level/temp3.csv")
                        .speeds(2, 4)
                        .seconds(5, 25, 4, 2)
                        .scores(15, 150, 750)
                        .build()
        );

        // Level 4, 5 ... 더 어려운 난이도 추가 가능
    }

    /**
     * 현재 레벨의 설정을 반환
     */
    public LevelConfig getCurrentLevelConfig() {
        return levels.get(currentLevelIndex);
    }

    /**
     * 다음 레벨이 존재하는지 확인
     */
    public boolean hasNextLevel() {
        return currentLevelIndex < levels.size() - 1;
    }

    /**
     * 다음 레벨로 상태를 변경
     */
    public void moveToNextLevel() {
        if (hasNextLevel()) {
            currentLevelIndex++;
        }
    }

    /**
     * 게임을 처음(1레벨)부터 다시 시작하기 위해 상태를 리셋
     * 점수도 0으로 초기화
     */
    public void reset() {
        currentLevelIndex = 0;
        currentScore = 0;
    }

    /**
     * 현재 레벨 번호를 반환 (1부터 시작)
     * 화면 표시용 (예: "LEVEL 1")
     */
    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1;
    }

    /**
     * 점수를 추가.
     */
    public void addScore(int score) {
        this.currentScore += score;
    }

    /**
     * 현재 총 점수를 반환.
     */
    public int getCurrentScore() {
        return currentScore;
    }
}
