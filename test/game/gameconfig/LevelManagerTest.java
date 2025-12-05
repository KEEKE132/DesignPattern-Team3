package game.gameconfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class LevelManagerTest {
    private LevelManager levelManager;

    @BeforeEach
    void setUp() {
        levelManager = new LevelManager();
    }

    @Test
    @DisplayName("초기화 시 레벨은 1이어야 한다.")
    void initialState() {
        assertSoftly(softly -> {
            softly.assertThat(levelManager.getCurrentLevelNumber()).isEqualTo(1);
            softly.assertThat(levelManager.getCurrentLevelConfig()).isNotNull();
                }
        );
    }

    @Test
    @DisplayName("다음 레벨로 이동하면 레벨 번호가 증가해야 한다.")
    void nextLevel() {
        int currentLevel = levelManager.getCurrentLevelNumber(); // 1

        // 다음 레벨이 있는지 확인
        boolean canMove = levelManager.hasNextLevel();

        // 실제로 이동
        if (canMove) {
            levelManager.moveToNextLevel();
        }

        assertThat(canMove).isTrue();
        assertThat(levelManager.getCurrentLevelNumber()).isEqualTo(currentLevel + 1);
    }

    @Test
    @DisplayName("마지막 레벨에서는 더 이상 이동할 수 없어야 한다.")
    void lastLevel() {
        while (levelManager.hasNextLevel()) {
            levelManager.moveToNextLevel();
        }

        // 현재 도착한 곳이 마지막 레벨임
        int lastLevelNumber = levelManager.getCurrentLevelNumber();

        // 마지막 레벨에서 한 번 더 이동 시도
        boolean hasNext = levelManager.hasNextLevel(); // false여야 함
        levelManager.moveToNextLevel(); // 이동 시도 (내부적으로 안 움직여야 함)

        assertThat(hasNext).isFalse();
        assertThat(levelManager.getCurrentLevelNumber()).isEqualTo(lastLevelNumber);
    }

    @Test
    @DisplayName("리셋하면 레벨은 1로 초기화되어야 한다.")
    void reset() {
        // 상태 변경 (레벨 이동)
        if (levelManager.hasNextLevel()) {
            levelManager.moveToNextLevel();
        }

        // 리셋 전 상태 확인
        assertThat(levelManager.getCurrentLevelNumber()).isNotEqualTo(1);

        // 리셋 실행
        levelManager.reset();

        // 리셋 후 상태 확인
        assertThat(levelManager.getCurrentLevelNumber()).isEqualTo(1);
    }
}
