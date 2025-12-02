package game.gameconfig;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.*;

class LevelConfigTest {
    @Test
    @DisplayName("올바른 설정값을 사용하여, LevelConfig 객체가 정상적으로 생성되어야 한다.")
    void valid() {
        final int FPS = 60;

        String validMap = "level/test.csv";
        int pacmanSpeed = 4;
        int ghostSpeed = 2;
        int frightenedTime = 10;
        int chaseTime = 20;
        int scatterTime = 5;
        int blinkTime = 2;
        int scorePacGum = 10;
        int scoreSuperPacGum = 50;
        int scoreGhostEaten = 200;

        LevelConfig config = LevelConfig.builder()
                .levelMap(validMap)
                .pacmanSpeed(pacmanSpeed)
                .ghostSpeed(ghostSpeed)
                .frightenedTimeSeconds(frightenedTime)
                .chaseTimeSeconds(chaseTime)
                .scatterTimeSeconds(scatterTime)
                .blinkTimeSeconds(blinkTime)
                .scorePacGum(scorePacGum)
                .scoreSuperPacGum(scoreSuperPacGum)
                .scoreGhostEaten(scoreGhostEaten)
                .build();

        assertSoftly(softly -> {
            // 맵 검증
            softly.assertThat(config.getLevelMap()).isEqualTo(validMap);

            // 속도 검증
            softly.assertThat(config.getPacmanSpeed()).isEqualTo(pacmanSpeed);
            softly.assertThat(config.getGhostSpeed()).isEqualTo(ghostSpeed);

            // 시간(프레임) 검증
            softly.assertThat(config.getFrightenedTimeFrames()).isEqualTo(frightenedTime * FPS);
            softly.assertThat(config.getChaseTimeFrames()).isEqualTo(chaseTime * FPS);
            softly.assertThat(config.getScatterTimeFrames()).isEqualTo(scatterTime * FPS);
            softly.assertThat(config.getBlinkTimeFrames()).isEqualTo(blinkTime * FPS);

            // 겁먹은 시간 및 깜빡임 시간 비교 검증
            softly.assertThat(config.getBlinkTimeFrames()).isLessThanOrEqualTo(config.getFrightenedTimeFrames());

            // 점수 검증
            softly.assertThat(config.getScorePacGum()).isEqualTo(scorePacGum);
            softly.assertThat(config.getScoreSuperPacGum()).isEqualTo(scoreSuperPacGum);
            softly.assertThat(config.getScoreGhostEaten()).isEqualTo(scoreGhostEaten);
        });
    }

    @ParameterizedTest
    @NullAndEmptySource // null과 ""(빈 문자열)을 자동으로 주입
    @DisplayName("맵 파일 경로가 비어있으면 예외가 발생해야 한다.")
    void invalidMapPath(String mapPath) {
        assertThatThrownBy(() -> LevelConfig.builder()
                .levelMap(mapPath)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 3, 5, 6, 7, 9})
    @DisplayName("속도가 8의 약수가 아니면 예외가 발생해야 한다.")
    void invalidSpeed(int speed) {
        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .pacmanSpeed(speed)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class);

            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .ghostSpeed(speed)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("시간 설정이 0 이하이면 예외가 발생해야 한다.")
    void invalidTime(int second) {
        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .frightenedTimeSeconds(second)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class);

            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .chaseTimeSeconds(second)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class);

            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .scatterTimeSeconds(second)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class);

            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .blinkTimeSeconds(second)
                            .build())
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    @DisplayName("깜빡임 시간이 겁먹은 시간보다 길면 예외가 발생해야 한다.")
    void invalidBlinkTime() {
        int frightenedTime = 5;
        int blinkTime = 6;

        assertThatThrownBy(() -> LevelConfig.builder()
                .frightenedTimeSeconds(frightenedTime)
                .blinkTimeSeconds(blinkTime)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("점수가 음수이면 예외가 발생해야 한다.")
    void invalidScore() {
        int score = -10;

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .scorePacGum(score)
                            .build())
                            .isInstanceOf(IllegalArgumentException.class);

            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .scoreSuperPacGum(score)
                            .build())
                            .isInstanceOf(IllegalArgumentException.class);

            softly.assertThatThrownBy(() -> LevelConfig.builder()
                            .scoreGhostEaten(score)
                            .build())
                            .isInstanceOf(IllegalArgumentException.class);
        });
    }
}
