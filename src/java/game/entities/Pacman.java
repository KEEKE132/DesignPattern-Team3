package game.entities;

import game.Game;
import game.Observer;
import game.Sujet;
import game.entities.ghosts.Ghost;
import game.gameconfig.LevelConfig;
import game.utils.CollisionDetector;
import game.utils.KeyHandler;
import game.utils.WallCollisionDetector;

import java.util.ArrayList;
import java.util.List;

//팩맨을 기술(설명)하기 위한 클래스
public class Pacman extends MovingEntity implements Sujet {
    private CollisionDetector collisionDetector;//충돌감지기
    private List<Observer> observerCollection; //UIPanel와 Game를 구독자로 관리

    // 생성자가 LevelConfig를 주입받도록 변경
    public Pacman(int xPos, int yPos, LevelConfig levelConfig) { // <-- 변경됨
        super(32, xPos, yPos, levelConfig.getPacmanSpeed(), "pacman.png", 4, 0.3f); // <-- 변경됨
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
        //팩맨이 팩껌, 슈퍼팩껌, 또는 유령과 접촉했는지 매번 테스트하고, 그에 따라 구독자들에게 알립니다.
        PacGum pg = (PacGum) collisionDetector.checkCollision(this, PacGum.class);
        if (pg != null) {
            notifyObserverPacGumEaten(pg);
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
}
