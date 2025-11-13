package game.gameconfig;

public class LevelConfig {
    // --- 설정 필드 ---
    private final String levelMap;

    private final int ghostSpeed;
    private final int pacmanSpeed;

    private final int frightenedTimeSeconds;
    private final int chaseTimeSeconds;
    private final int scatterTimeSeconds;
    private final int blinkTimeSeconds;

    private final int scorePacGum;
    private final int scoreSuperPacGum;
    private final int scoreGhostEaten;


    private static final int FPS = 60; // 타이머 계산을 위한 기준값

    public LevelConfig(LevelBuilder builder) {
        this.levelMap = builder.levelMap;

        this.ghostSpeed = builder.ghostSpeed;
        this.pacmanSpeed = builder.pacmanSpeed;

        this.frightenedTimeSeconds = builder.frightenedTimeSeconds;
        this.chaseTimeSeconds = builder.chaseTimeSeconds;
        this.scatterTimeSeconds = builder.scatterTimeSeconds;
        this.blinkTimeSeconds = builder.blinkTimeSeconds;

        this.scorePacGum = builder.scorePacGum;
        this.scoreSuperPacGum = builder.scoreSuperPacGum;
        this.scoreGhostEaten = builder.scoreGhostEaten;
    }

    // Getter
    public String getLevelMap() { return levelMap; }

    public int getGhostSpeed() { return ghostSpeed; }
    public int getPacmanSpeed() { return pacmanSpeed; }

    // 초(second) * FPS, 즉 Frame 반환
    public int getFrightenedTimeFrames() { return frightenedTimeSeconds * FPS; }
    public int getChaseTimeFrames() { return chaseTimeSeconds * FPS; }
    public int getScatterTimeFrames() { return scatterTimeSeconds * FPS; }
    public int getBlinkTimeFrames() {
        return blinkTimeSeconds * FPS;
    }

    public int getScorePacGum() { return scorePacGum; }
    public int getScoreSuperPacGum() { return scoreSuperPacGum; }
    public int getScoreGhostEaten() { return scoreGhostEaten; }

    //빌더 객체를 생성하는 정적 팩토리 메서드 (new를 쓰지 않기 위함)
    public static LevelBuilder builder() {
        return new LevelBuilder();
    }

    public static class LevelBuilder {
        // 기존 하드코딩 값으로 '기본레벨 값' 설정
        private String levelMap = "level/level.csv";

        private int ghostSpeed = 2;
        private int pacmanSpeed = 2;

        private int frightenedTimeSeconds = 7;
        private int chaseTimeSeconds = 20;
        private int scatterTimeSeconds = 5;
        private int blinkTimeSeconds = 2;

        private int scorePacGum = 10;
        private int scoreSuperPacGum = 100;
        private int scoreGhostEaten = 500;

        //외부에서 'new GameLevelConfig.GameLevelBuilder()' 호출을 막고,
        //'GameLevelConfig.builder()' 메서드만 사용하도록 강제
        private LevelBuilder() {}

        //빌더 자신(this)을 반환하여 메서드 체이닝 가능
        public LevelBuilder levelMap(String levelMap) {
            this.levelMap = levelMap;
            return this;
        }

        // --- 속도 설정 ---
        //게임의 모든 로직(벽, 방향 전환, 아이템)이 8x8 픽셀 그리드를 기준으로 작동하도록 설계
        //게임이 정상적으로 작동(벽에 끼이지 않고, 아이템을 먹을 수 있는)하는 모든 speed 값은 8의 약수
        //즉, speed는 1, 2, 4, 8만 가능! (유령, 팩맨 모두)
        public LevelBuilder ghostSpeed(int ghostSpeed) {
            this.ghostSpeed = ghostSpeed;
            return this;
        }
        public LevelBuilder pacmanSpeed(int pacmanSpeed) {
            this.pacmanSpeed = pacmanSpeed;
            return this;
        }
        public LevelBuilder speeds(int ghost, int pacman) {
            this.ghostSpeed = ghost;
            this.pacmanSpeed = pacman;
            return this;
        }

        // --- 시간 설정 (초 단위) ---
        public LevelBuilder frightenedTimeSeconds(int seconds) {
            this.frightenedTimeSeconds = seconds;
            return this;
        }
        public LevelBuilder chaseTimeSeconds(int seconds) {
            this.chaseTimeSeconds = seconds;
            return this;
        }
        public LevelBuilder scatterTimeSeconds(int seconds) {
            this.scatterTimeSeconds = seconds;
            return this;
        }
        public LevelBuilder blinkTimeSeconds(int seconds) {
            this.blinkTimeSeconds = seconds;
            return this;
        }
        public LevelBuilder seconds(int frightened, int chase, int scatter, int blink) {
            this.frightenedTimeSeconds = frightened;
            this.scatterTimeSeconds = scatter;
            this.chaseTimeSeconds = chase;
            this.blinkTimeSeconds = blink;
            return this;
        }

        // --- 점수 설정 ---
        public LevelBuilder scorePacGum(int score) {
            this.scorePacGum = score;
            return this;
        }
        public LevelBuilder scoreSuperPacGum(int score) {
            this.scoreSuperPacGum = score;
            return this;
        }
        public LevelBuilder scoreGhostEaten(int score) {
            this.scoreGhostEaten = score;
            return this;
        }
        public LevelBuilder scores(int pacGum, int superPacGum, int ghost) {
            this.scorePacGum = pacGum;
            this.scoreSuperPacGum = superPacGum;
            this.scoreGhostEaten = ghost;
            return this;
        }

        public LevelConfig build() {
            //--- 유효성 검사 로직
            //1. 맵 파일 이름 검사
            if (levelMap == null || levelMap.trim().isEmpty()) {
                throw new IllegalArgumentException("맵 파일 경로는 null이거나 비어있을 수 없습니다.");
            }

            //2. 속도 검사 (8의 약수인지 확인)
            if (pacmanSpeed <= 0 || 8 % pacmanSpeed != 0) {
                throw new IllegalArgumentException("팩맨의 속도는 8의 약수(1, 2, 4, 8)여야 합니다. 현재 값: " + pacmanSpeed);
            }
            if (ghostSpeed <= 0 || 8 % ghostSpeed != 0) {
                throw new IllegalArgumentException("유령의 속도는 8의 약수(1, 2, 4, 8)여야 합니다. 현재 값: " + ghostSpeed);
            }

            //3. 시간 검사 (0초 이하 방지)
            if (frightenedTimeSeconds <= 0 || chaseTimeSeconds <= 0 || scatterTimeSeconds <= 0 || blinkTimeSeconds <= 0) {
                throw new IllegalArgumentException("모든 시간 설정은 0초보다 커야 합니다.");
            }

            //4. 깜빡임 시간 및 겁먹은 시간 논리 검사
            if (frightenedTimeSeconds < blinkTimeSeconds) {
                throw new IllegalArgumentException(
                        "겁먹은 상태 지속시간(" + frightenedTimeSeconds + "초)은 " +
                                "깜빡임 지속시간(" + blinkTimeSeconds + "초)보다 짧을 수 없습니다."
                );
            }

            //5. 점수 검사 (음수 방지)
            if (scorePacGum < 0 || scoreSuperPacGum < 0 || scoreGhostEaten < 0) {
                throw new IllegalArgumentException("점수는 음수가 될 수 없습니다.");
            }

            return new LevelConfig(this); // 빌더 자신을 넘겨 '제품' 생성
        }
    }
}