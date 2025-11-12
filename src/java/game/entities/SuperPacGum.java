package game.entities;

import java.awt.*;

//슈퍼 팩껌(큰 점)을 위한 클래스
public class SuperPacGum extends StaticEntity {
    private int frameCount = 0;

    public SuperPacGum(int xPos, int yPos) {
        super(16, xPos, yPos); // 오프셋이 없는 것으로 보아, 맵 타일(아마도 16x16) 하나를 꽉 채우는 크기
    }

    @Override
    public void render(Graphics2D g) {
        //슈퍼 팩껌이 깜빡이도록, 60프레임 중 30프레임만 그립니다.
        //이 게임은 1초에 60번 업데이트(60 FPS)되는 것을 목표로 만들어졌습니다.
        if (frameCount % 60 < 30) {
            g.setColor(new Color(255, 183, 174));
            g.fillOval(this.xPos, this.yPos, this.size, this.size); //4x4 크기의 채워진 원을 그립니다.
        }
    }

    //SuperPacGum이 깜빡일 수 있도록 시간을 흐르게(카운터를 증가) 하는 역할
    @Override
    public void update() {
        frameCount++;
    }
}
