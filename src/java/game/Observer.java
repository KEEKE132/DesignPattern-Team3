package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;

//구독자 인터페이스 -> UIPanel, Game
public interface Observer {
    void updatePacGumEaten(PacGum pg); //"팩맨이 팩껌을 먹었다"는 알림을 받았을 때 실행
    void updateSuperPacGumEaten(SuperPacGum spg); //"팩맨이 슈퍼 팩껌을 먹었다"는 알림을 받았을 때 실행
    void updateGhostCollision(Ghost gh); //"팩맨이 유령과 충돌했다"는 알림을 받았을 때 실행
    void updateItemEaten(Item item);
}