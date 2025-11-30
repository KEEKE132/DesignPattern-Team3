package game.entities;

import game.Observer;
import game.Sujet;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;
import game.utils.CollisionDetector;
import game.utils.KeyHandler;
import game.utils.WallCollisionDetector;

import java.util.ArrayList;
import java.util.List;

//팩맨을 기술(설명)하기 위한 클래스
public class Pacman extends MovingEntity implements Sujet {
    private CollisionDetector collisionDetector;//충돌감지기
    private List<Observer> observerCollection; //UIPanel와 Game를 구독자로 관리

    // 속도 버프(Haste Item) 관리를 위한 필드
    private int hasteFrameTimer = 0;     // 남은 지속 시간 (프레임 단위)
    private int defaultSpd;              // 원래 속도 저장용

    public Pacman(int xPos, int yPos, int spd) {
        super(32, xPos, yPos, spd, "pacman.png", 4, 0.3f);
        defaultSpd = spd; // 생성 시 기본 속도를 저장
        observerCollection = new ArrayList<>(); //팩맨의 "구독자" 목록을 초기화
    }

    //이동 관리
    //KeyListener로부터 키 입력(k)을 받아 팩맨의 다음 이동 방향을 결정
    public void input(KeyHandler k) {
        int new_xSpd = 0;
        int new_ySpd = 0;

        if (!onTheGrid()) return; //팩맨은 게임 영역의 "칸" 위에 있어야 합니다 (즉, 그리드 정렬)
        if (!onGameplayWindow()) return; //팩맨은 게임 영역 내에 있어야 합니다.

        //눌린 키에 따라, 팩맨의 방향이 그에 맞춰 변경됩니다.
        //!WallCollisionDetector.checkWallCollision-> 키가 눌렸을 때, "만약 그 방향으로 이동한다면" 벽과 부딪히는지 미리 확인
        //벽이 없을 때만 new_xSpd, new_ySpd (새로운 속도)를 설정
        if (k.k_left.isPressed && xSpd >= 0 && !WallCollisionDetector.checkWallCollision(this, -spd, 0)) {
            new_xSpd = -spd;
        }
        if (k.k_right.isPressed && xSpd <= 0 && !WallCollisionDetector.checkWallCollision(this, spd, 0)) {
            new_xSpd = spd;
        }
        if (k.k_up.isPressed && ySpd >= 0 && !WallCollisionDetector.checkWallCollision(this, 0, -spd)) {
            new_ySpd = -spd;
        }
        if (k.k_down.isPressed && ySpd <= 0 && !WallCollisionDetector.checkWallCollision(this, 0, spd)) {
            new_ySpd = spd;
        }

        if (new_xSpd == 0 && new_ySpd == 0) return;

        // if (!Game.getFirstInput()) Game.setFirstInput(true); // firstInput는 PlayingState에서 관리

        //만약 유저가 상/하와 좌/우를 동시에 눌러도 대각선으로 가지 않도록, 한 번에 한 방향(수평 또는 수직)으로만 움직이게 보정하는 로직
        if (Math.abs(new_xSpd) != Math.abs(new_ySpd)) {
            xSpd = new_xSpd;
            ySpd = new_ySpd;
        } else {
            if (xSpd != 0) {
                xSpd = 0;
                ySpd = new_ySpd;
            }else{
                xSpd = new_xSpd;
                ySpd = 0;
            }
        }
    }

    @Override
    public void update() {
        // 매 프레임마다 버프 시간 체크
        // (PausedState에서는 update()가 호출되지 않으므로 타이머도 멈추게 됩니다.)
        updateHasteState();

        //팩맨이 팩껌, 슈퍼팩껌, 또는 유령과 접촉했는지 매번 테스트하고, 그에 따라 구독자들에게 알립니다.
        PacGum pg = (PacGum) collisionDetector.checkCollision(this, PacGum.class);
        if (pg != null) {
            notifyObserverPacGumEaten(pg);
        }

        Item item = (Item) collisionDetector.checkCollision(this, Item.class);
        if (item != null) {
            notifyObserverItemEaten(item);
        }

        SuperPacGum spg = (SuperPacGum) collisionDetector.checkCollision(this, SuperPacGum.class);
        if (spg != null) {
            notifyObserverSuperPacGumEaten(spg);
        }

        Ghost gh = (Ghost) collisionDetector.checkCollision(this, Ghost.class);
        if (gh != null) {
            notifyObserverGhostCollision(gh);
        }

        //만약 팩맨의 다음 잠재적 위치에 벽이 없다면, 위치를 업데이트합니다.
        //input 메서드에서 설정된 xSpd, ySpd 방향으로 실제 이동(updatePosition())을 시도
        //이때 다시 한번 벽 충돌을 확인하여, 벽으로 막혀있으면 이동하지 않습니다. (팩맨이 벽에 박히는 것을 방지)
        if (!WallCollisionDetector.checkWallCollision(this, xSpd, ySpd)) {
            updatePosition();
        }
    }

    /**
     * 프레임마다 호출되어 버프 시간을 줄이고, 시간이 다 되면 속도를 복구함
     */
    private void updateHasteState() {
        if (hasteFrameTimer > 0) {
            hasteFrameTimer--; // 1 프레임 감소

            // 시간이 다 되면 원래 속도로 복귀
            if (hasteFrameTimer == 0) {
                setSpd(defaultSpd);
            }
        }
    }

    /**
     * 외부(아이템)에서 속도 버프를 적용할 때 호출하는 메서드
     * @param durationFrames 지속할 프레임 수 (예: 5초 * 60FPS = 300)
     */
    public void applyHaste(int durationFrames) {
        // 이미 버프 중이 아닐 때만 원래 속도를 저장 (중복 적용 시 원래 속도가 덮어써지는 것 방지)
        if (hasteFrameTimer == 0) {
            defaultSpd = spd;
        }

        // 지속 시간 내에 여러 개 먹으면 속도가 중첩됨
        int newSpeed = spd * 2;

        // 속도 증가 (최대 속도는 8로 제한 - 그리드 크기가 8이라서 너무 빠르면 벽 뚫음 방지)
        setSpd(Math.min(newSpeed, 8));

        // 시간 설정 (이미 버프 중이면 시간만 다시 연장됨)
        hasteFrameTimer = durationFrames;
    }

    public void setCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    @Override
    public void registerObserver(Observer observer) {
        observerCollection.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObserverPacGumEaten(PacGum pg) {
        observerCollection.forEach(obs -> obs.updatePacGumEaten(pg));
    }

    @Override
    public void notifyObserverSuperPacGumEaten(SuperPacGum spg) {
        observerCollection.forEach(obs -> obs.updateSuperPacGumEaten(spg));
    }

    @Override
    public void notifyObserverGhostCollision(Ghost gh) {
        observerCollection.forEach(obs -> obs.updateGhostCollision(gh));
    }

    @Override
    public void notifyObserverItemEaten(Item item) {
        observerCollection.forEach(obs -> obs.updateItemEaten(item));
    }
}
