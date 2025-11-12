package game.entities;

import java.awt.*;

//팩껌(작은 점)을 위한 클래스
public class PacGum extends StaticEntity {
    //level.csv 맵 파일에서 읽어온 xPos, yPos는 아마도 타일의 왼쪽 위 꼭짓점일 것입니다.
    //여기에 +8을 더하는 것은, 그 타일의 중앙 부근에 이 작은 점을 배치하기 위한 '오프셋(Offset)'
    public PacGum(int xPos, int yPos) {
        super(4, xPos + 8, yPos + 8);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(255, 183, 174));
        g.fillRect(xPos, yPos, size, size); //4x4 크기의 채워진 사각형을 그립니다.
    }
}