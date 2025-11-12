package game.entities;

//벽을 위한 클래스
public class Wall extends StaticEntity {
    public Wall(int xPos, int yPos) {
        super(8, xPos, yPos); //size는 8로 고정
    }
}
