package game.utils;

import game.Game;
import game.entities.*;

//엔티티 간의 충돌을 감지하는 클래스.
//게임 내의 모든 동적 물체(팩맨, 유령, 껌) 사이의 충돌을 검사
public class CollisionDetector {
    private Game game;

    public CollisionDetector(Game game) {
        this.game = game;
    }

    //collisionCheck 타입의 엔티티와 obj 엔티티 간의 충돌 감지; 충돌 시 테스트된 타입의 엔티티를 반환합니다.
    //collisionCheck 타입의 엔티티는 사각형 히트박스를 가지며, 여기서는 obj 엔티티의 히트박스를 점(point)으로 간주합니다.
    //(팩맨과 유령의 충돌의 경우, 이것은 여유를 주어 게임이 너무 가혹하지 않게 만듭니다).
    public Entity checkCollision(Entity obj, Class<? extends Entity> collisionCheck) {
        for (Entity e : game.getEntities()) {
            if (!e.isDestroyed() && collisionCheck.isInstance(e) && e.getHitbox().contains(obj.getxPos() + obj.getSize() / 2, obj.getyPos() + obj.getSize() / 2)) return e;
        }
        return null;
    }

    //이전 메서드와 동일하지만, 모든 히트박스를 '사각형(Rectangle)'으로 간주합니다.
    //이전 방식보다 훨씬 더 빡빡하고 정확한,"픽셀 단위"의 충돌 판정이 필요할 때 사용
    public Entity checkCollisionRect(Entity obj, Class<? extends Entity> collisionCheck) {
        for (Entity e : game.getEntities()) {
            if (!e.isDestroyed() && collisionCheck.isInstance(e) && e.getHitbox().intersects(obj.getHitbox())) return e;
        }
        return null;
    }
}
