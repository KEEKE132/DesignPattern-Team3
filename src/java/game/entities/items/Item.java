package game.entities.items;

import game.Game;
import game.entities.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Item extends StaticEntity {

    protected BufferedImage itemImage;

    public Item(int xPos, int yPos, String imagePath) {
        super(16, xPos, yPos);

        try {
            this.itemImage = ImageIO.read(getClass().getClassLoader().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 아이템을 먹은 경우 행동 방식 구현 메서드
    public abstract void onEaten(Game game);

    // 클리어에 필요한 아이템인지 판단하기 위한 메서드
    public boolean isRequiredToClear() {
        return false;
    }

    @Override
    public void render(Graphics2D g) {
        if (itemImage != null) {
            int size = 16; // 그릴 이미지 크기
            g.drawImage(itemImage, xPos, yPos, size, size, null);
        }
    }

}
