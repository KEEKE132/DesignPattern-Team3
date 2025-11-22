package game.entities;

import game.GameplayPanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

//움직이는 엔티티(물체)를 기술(설명)하기 위한 추상 클래스
public abstract class MovingEntity extends Entity {
    protected int spd; //기본 속도
    protected int xSpd = 0; //수평 속도
    protected int ySpd = 0; //수직 속도

    protected BufferedImage sprite; //팩맨이나 유령의 '스프라이트 시트' 이미지 파일 전체
    protected float subimage = 0; //현재 애니메이션 프레임이 몇 번째인지
    protected int nbSubimagesPerCycle; //한 방향의 애니메이션이 몇 개의 프레임으로 구성되는지 (예: 팩맨이 입을 벌렸다 닫는 4개 프레임)
    protected int direction = 0; //현재 바라보는 방향 (0: 오른쪽, 1: 왼쪽, 2: 위, 3: 아래)
    protected float imageSpd = 0.2f; //애니메이션이 얼마나 빨리 재생될지 (값이 클수록 빠름)

    public MovingEntity(int size, int xPos, int yPos, int spd, String spriteName, int nbSubimagesPerCycle, float imageSpd) {
        super(size, xPos, yPos);
        this.spd = spd;
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("img/" + spriteName));
            this.nbSubimagesPerCycle = nbSubimagesPerCycle;
            this.imageSpd = imageSpd;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        updatePosition();
    }

    public void updatePosition() {
        //엔티티(물체)의 위치 업데이트
        if (!(xSpd == 0 && ySpd == 0)) { //만약 수평 속도나 수직 속도가 0이 아니면, 그에 맞춰 수평 및 수직 위치를 증가시킵니다.
            xPos+=xSpd;
            yPos+=ySpd;

            //이동 방향에 따라, 'direction' 값을 변경합니다 (이 정수 값은 특히 이미지의 어느 부분을 표시할지 아는 데 사용됩니다).
            if (xSpd > 0) {
                direction = 0;
            } else if (xSpd < 0) {
                direction = 1;
            } else if (ySpd < 0) {
                direction = 2;
            } else if (ySpd > 0) {
                direction = 3;
            }

            //애니메이션 프레임 변경
            //표시할 애니메이션의 현재 이미지 값(프레임)을 증가시키고(속도는 다를 수 있음), 애니메이션의 이미지 수에 따라 값은 루프를 돕니다(순환)
            subimage += imageSpd;
            if (subimage >= nbSubimagesPerCycle) {
                subimage = 0;
            }
        }

        //만약 엔티티가 게임 영역의 경계를 벗어나면, 반대편으로 넘어갑니다.
        if (xPos > GameplayPanel.width) {
            xPos = 0 - size + spd;
        }

        if (xPos < 0 - size + spd) {
            xPos = GameplayPanel.width;
        }

        if (yPos > GameplayPanel.height) {
            yPos = 0 - size + spd;
        }

        if (yPos < 0 - size + spd) {
            yPos = GameplayPanel.height;
        }
    }

    @Override
    public void render(Graphics2D g) {
        //기본적으로, 각 "스프라이트"는 4개의 방향에 해당하는 4가지 애니메이션 변형을 포함하고, 각 애니메이션은 특정 수의 이미지를 갖는다고 가정합니다.
        //이것을 바탕으로, 올바른 방향과 올바른 애니메이션 프레임에 해당하는 스프라이트 이미지의 '부분'만 표시합니다.
        g.drawImage(sprite.getSubimage((int)subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size), this.xPos, this.yPos,null);
    }

    //엔티티가 게임 영역의 그리드 칸에 올바르게 위치해 있는지 아닌지 알기 위한 메서드
    //이 게임의 그리드(칸)는 Wall의 size였던 8x8 크기임을 알 수 있습니다.
    //이 메서드는 팩맨이나 유령이 '교차로'에 정확히 도착했는지 확인합니다.
    //Ghost는 onTheGrid()가 true일 때만 (즉, 교차로 중앙에 있을 때만) "이제 왼쪽으로 갈까? 오른쪽으로 갈까?"를 결정할 수 있습니다.
    //만약 이 메서드가 없다면, Ghost는 벽과 벽 사이 애매한 위치에서도 방향을 틀려고 하다가 벽에 끼일 것입니다.
    public boolean onTheGrid() {
        return (xPos%8 == 0 && yPos%8 == 0);
    }

    //엔티티가 게임 영역 내에 있는지 아닌지 알기 위한 메서드
    public boolean onGameplayWindow() {
        return !(xPos<=0 || xPos>= GameplayPanel.width || yPos<=0 || yPos>= GameplayPanel.height);
    }

    //StaticEntity와 달리, new Rectangle(...)을 매번 새로 생성해서 반환
    //xPos, yPos가 매 프레임마다 변하기 때문에, 현재 위치를 기준으로 한 '최신' 히트박스를 반환
    public Rectangle getHitbox() {
        return new Rectangle(xPos, yPos, size, size);
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    //이미 메모리에 로드된 BufferedImage 객체를 직접 sprite 변수에 할당(설정)할 때 사용
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    //파일 이름(String)을 받아서, 리소스 폴더(img/)에서 해당 파일을 새로 읽어온(Load) 뒤 sprite 변수에 설정할 때 사용
    public void setSprite(String spriteName) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("img/" + spriteName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getSubimage() {
        return subimage;
    }
    public void setSubimage(float subimage) {
        this.subimage = subimage;
    }

    public int getNbSubimagesPerCycle() {
        return nbSubimagesPerCycle;
    }
    public void setNbSubimagesPerCycle(int nbSubimagesPerCycle) {
        this.nbSubimagesPerCycle = nbSubimagesPerCycle;
    }

    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getxSpd() {
        return xSpd;
    }
    public void setxSpd(int xSpd) {
        this.xSpd = xSpd;
    }

    public int getySpd() {
        return ySpd;
    }
    public void setySpd(int ySpd) {
        this.ySpd = ySpd;
    }

    public int getSpd() {
        return spd;
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }
}
