package game;

import game.entities.PacGum;
import game.entities.SuperPacGum;
import game.entities.ghosts.Ghost;
import game.entities.items.Item;

//발행자 인터페이스 -> Pacman
public interface Sujet {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserverPacGumEaten(PacGum pg);
    void notifyObserverSuperPacGumEaten(SuperPacGum spg);
    void notifyObserverGhostCollision(Ghost gh);
    void notifyObserverItemEaten(Item item);
}
