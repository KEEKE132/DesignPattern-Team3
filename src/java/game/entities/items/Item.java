package game.entities.items;

import game.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Item extends StaticEntity {

    protected final int scoreValue;
    protected BufferedImage itemImage;

    public Item(int xPos, int yPos, int score, String imagePath) {
        super(16, xPos, yPos);
        this.scoreValue = score;

        try {
            this.itemImage = ImageIO.read(getClass().getClassLoader().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getScoreValue() {
        return scoreValue;
    }

    @Override
    public void render(Graphics2D g) {
        if (itemImage != null) {
            int size = 16; // 그릴 이미지 크기

            /* * [2. 렌더링 위치 보정]
             * - 현재 xPos는 4px짜리 점(팩껌)의 시작점입니다.
             * - 팩껌의 중심: xPos + 2
             * - 이미지(32px)의 중심: (그릴 위치) + 16
             * - (그릴 위치) + 16 = xPos + 2 가 되어야 하므로,
             * - (그릴 위치) = xPos - 14 가 됩니다.
             */
            g.drawImage(itemImage, xPos, yPos, size, size, null);
        }
    }

}
