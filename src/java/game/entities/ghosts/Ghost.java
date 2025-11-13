package game.entities.ghosts;

import game.Game;
import game.entities.MovingEntity;
import game.gameconfig.LevelConfig;
import game.ghostStates.*;
import game.ghostStrategies.IGhostStrategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//유령을 기술(설명)하기 위한 추상 클래스
public abstract class Ghost extends MovingEntity {
    protected GhostState state;

    protected final GhostState chaseMode;
    protected final GhostState scatterMode;
    protected final GhostState frightenedMode;
    protected final GhostState eatenMode;
    protected final GhostState houseMode;

    protected int modeTimer = 0;
    protected int frightenedTimer = 0;
    protected boolean isChasing = false;

    protected static BufferedImage frightenedSprite1;
    protected static BufferedImage frightenedSprite2;
    protected static BufferedImage eatenSprite;

    protected IGhostStrategy strategy; //각 유령의 고유한 '추격 AI(알고리즘)'를 '객체(전략)'로 캡슐화

    private final LevelConfig levelConfig; // <-- 추가

    // 생성자가 LevelConfig를 주입받도록 변경
    public Ghost(int xPos, int yPos, String spriteName, LevelConfig levelConfig) {
        super(32, xPos, yPos, levelConfig.getGhostSpeed(), spriteName, 2, 0.1f);
        this.levelConfig = levelConfig; // <-- 추가

        //유령의 여러 가지 상태(State) 생성
        chaseMode = new ChaseMode(this);
        scatterMode = new ScatterMode(this);
        frightenedMode = new FrightenedMode(this);
        eatenMode = new EatenMode(this);
        houseMode = new HouseMode(this);

        //state라는 변수가 현재 5가지 상태 중 '현재 상태'가 무엇인지 가리킵니다.
        //(초기 상태는 houseMode)
        state = houseMode; //초기 상태

        try {
            frightenedSprite1 = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_frightened.png"));
            frightenedSprite2 = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_frightened_2.png"));
            eatenSprite = ImageIO.read(getClass().getClassLoader().getResource("img/ghost_eaten.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //여러 상태 간의 전환을 위한 메서드
    public void switchChaseMode() {
        state = chaseMode;
    }
    public void switchScatterMode() {
        state = scatterMode;
    }
    public void switchFrightenedMode() {
        frightenedTimer = 0;
        state = frightenedMode;
    }
    public void switchEatenMode() {
        state = eatenMode;
    }
    public void switchHouseMode() {
        state = houseMode;
    }

    public void switchChaseModeOrScatterMode() {
        if (isChasing) {
            switchChaseMode();
        }else{
            switchScatterMode();
        }
    }

    public IGhostStrategy getStrategy() {
        return this.strategy;
    }
    public void setStrategy(IGhostStrategy strategy) {
        this.strategy = strategy;
    }

    public GhostState getState() {
        return state;
    }

    @Override
    public void update() {
        if (!Game.getFirstInput()) return; //플레이어가 움직이기 전까지 유령은 움직이지 않습니다.

        //만약 유령이 '겁먹은' 상태라면, 설정된 시간(levelConfig)만큼 타이머가 시작되며,
        //시간이 다 되면 적절한 전환을 적용하기 위해 해당 상태(state)에 알림을 보내, 유령을 원래 상태로 되돌립니다.
        if (state == frightenedMode) {
            frightenedTimer++;

            if (frightenedTimer >= levelConfig.getFrightenedTimeFrames()) { // <-- 변경됨
                state.timerFrightenedModeOver();
            }
        }

        //유령들은 타이머에 따라 '추격(chase)' 상태와 '흩어지기(scatter)' 상태를 번갈아 가며 수행합니다.
        //만약 유령이 '추격' 또는 '흩어지기' 상태일 때, 타이머가 시작되며,
        //상태에 따라 설정된 시간(chase/scatter time)이 지나면 적절한 전환을 적용하기 위해 해당 상태(state)에 알림을 보냅니다.
        if (state == chaseMode || state == scatterMode) {
            modeTimer++;

            //(추격)일 땐 chaseTime, (흩어지기)일 땐 (scatterTime)만큼 지속 //
            if ((isChasing && modeTimer >= levelConfig.getChaseTimeFrames()) || (!isChasing && modeTimer >= levelConfig.getScatterTimeFrames())) { // <-- 변경됨
                state.timerModeOver();
                isChasing = !isChasing;
            }
        }

        //만약 유령이 유령의 집 바로 위 칸에 도착하면, 적절한 전환을 위해 해당 상태(state)에 알림을 보냅니다.
        if (xPos == 208 && yPos == 168) {
            state.outsideHouse();
        }

        //만약 유령이 유령의 집 중앙 칸에 도착하면, 적절한 전환을 위해 해당 상태(state)에 알림을 보냅니다
        if (xPos == 208 && yPos == 200) {
            state.insideHouse();
        }

        //상태(state)에 따라, 유령은 다음 방향을 계산하고, 그 뒤 위치를 업데이트합니다.
        state.computeNextDir();
        updatePosition();
    }

    @Override
    public void render(Graphics2D g) {
        //유령의 상태(state)에 따라 다른 스프라이트가 사용됩니다 (생각해보니, GhostState 안에 render 메서드를 만드는 것이 더 현명했을지도 모르겠네요).
        if (state == frightenedMode) { //겁먹은' 상태일 때 파란색 유령(frightenedSprite1)을 그립니다.
            //1.'깜빡임 시점(프레임)'을 계산
            int blinkStartTimeFrame = levelConfig.getFrightenedTimeFrames() - levelConfig.getBlinkTimeFrames();

            //두려움 시간(frightenedTimeSeconds) 중, 깜빡임 시간(blinkTimeSeconds)이 되면 파란색/하얀색을 번갈아 그리며 "곧 풀린다"는 깜빡임 효과를 줍니다.
            if (frightenedTimer <= blinkStartTimeFrame  || frightenedTimer%20 > 10) {
                g.drawImage(frightenedSprite1.getSubimage((int)subimage * size, 0, size, size), this.xPos, this.yPos,null);
            }else{
                g.drawImage(frightenedSprite2.getSubimage((int)subimage * size, 0, size, size), this.xPos, this.yPos,null);
            }
        }else if (state == eatenMode) { //팩맨에게 먹히면 눈알만 남은 스프라이트(eatenSprite)를 그립
            g.drawImage(eatenSprite.getSubimage(direction * size, 0, size, size), this.xPos, this.yPos,null);
        }else{ //자신의 고유한 색상 스프라이트(sprite)를 그립니다.
            g.drawImage(sprite.getSubimage((int)subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size), this.xPos, this.yPos,null);
        }

    }
}
