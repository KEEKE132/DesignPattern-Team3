package game.ghostVisitor;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;
import game.entities.ghosts.Pinky;

public class StartLineVisitor implements GhostVisitor {

    @Override
    public void visit(Ghost target) {
        target.setDialogue("Hi I`m Ghost!");
    }

    @Override
    public void visit(Blinky target) {
        target.setDialogue("Hi I`m Blinky!");
    }

    @Override
    public void visit(Clyde target) {
        target.setDialogue("Hi I`m Clyde!");
    }

    @Override
    public void visit(Inky target) {
        target.setDialogue("Hi I`m Inky!");
    }

    @Override
    public void visit(Pinky target) {
        target.setDialogue("Hi I`m Pinky!");
    }
}
