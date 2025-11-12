package game.utils;

import game.Game;
import game.entities.Entity;
import game.entities.GhostHouse;
import game.entities.Wall;

import java.awt.*;

//엔티티와 '벽'의 충돌을 감지하는 클래스 (벽은 '정적'임).
public class WallCollisionDetector {

    //이 '델타'(dx, dy) 값은 엔티티가 벽에 부딪히기 '전에' 벽을 감지하게 해줍니다.
    public static boolean checkWallCollision(Entity obj, int dx, int dy) {
        //obj가 이동할 '미래의 위치'에 가상의 히트박스(r)를 미리 만듭니다.
        Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
        for (Wall w : Game.getWalls()) { //'미래의 히트박스'가 벽과 겹치는지 검사
            if (w.getHitbox().intersects(r)) return true;
        }
        return false;
    }

    //여기서는 '유령의 집' 벽과의 충돌을 무시할 수 있습니다.
    public static boolean checkWallCollision(Entity obj, int dx, int dy, boolean ignoreGhostHouses) {
        Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
        for (Wall w : Game.getWalls()) {
            //ignoreGhostHouses 플래그가 true이고, 지금 검사하는 벽(w)이 GhostHouse 타입이라면, 그 충돌은 무시해라
            //팩맨은 GhostHouse 문을 통과할 수 없습니다.
            //유령은 GhostHouse 문을 자유롭게 통과합니다.
            if (!(ignoreGhostHouses && w instanceof GhostHouse) && w.getHitbox().intersects(r)) return true;
        }
        return false;
    }
}