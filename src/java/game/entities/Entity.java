package game.entities;

import java.awt.*;

//엔티티(물체)를 기술(설명)하기 위한 추상 클래스
public abstract class Entity {
    protected int size;
    protected int xPos;
    protected int yPos;

    protected boolean destroyed = false;

    public Entity(int size, int xPos, int yPos) {
        this.size = size;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void update() {}

    //Graphics2D은 붓과 캔버스 역할
    public void render(Graphics2D g) {}

    //-32는 Entity를 "화면 밖"으로 확실하게 치워버리기 위한 값입니다.
    //destroyed = true; 플래그를 설정하면, 게임의 메인 루프(Game Loop)가 이 객체를 더 이상 업데이트하거나 그리지 않는 것이 정석입니다.
    //하지만 만약 메인 루프가 isDestroyed() 체크를 깜빡하거나, CollisionDetector가 실수로 이미 죽은 객체를 검사하는 버그가 생길 수 있습니다.
    //이때 객체가 (-32, -32)라는 '안전한' 화면 밖 좌표에 가 있다면, 실수로 화면에 그려지거나 다른 객체와 충돌할 일이 원천적으로 차단됩니다.
    public void destroy() {
        this.xPos = -32;
        this.yPos = -32;
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public int getSize() {
        return size;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    //충돌 감지를 위한 '보이지 않는 사각형(범위)'을 반환
    //CollisionDetector(충돌 감지기)가 팩맨과 유령이 부딪혔는지 검사
    public abstract Rectangle getHitbox();
}
