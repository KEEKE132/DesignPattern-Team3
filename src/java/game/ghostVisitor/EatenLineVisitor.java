package game.ghostVisitor;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;
import game.entities.ghosts.Pinky;

public class EatenLineVisitor implements GhostVisitor {

    @Override
    public void visit(Ghost target) {
        target.setDialogue("I've been eaten!");
    }

    @Override
    public void visit(Blinky target) {
        target.setDialogue("Nooo! I'll be back!");
    }

    @Override
    public void visit(Clyde target) {
        target.setDialogue("Ouch! That hurt!");
    }

    @Override
    public void visit(Inky target) {
        target.setDialogue("I knew this would happen...");
    }

    @Override
    public void visit(Pinky target) {
        target.setDialogue("So close!\nI almost had him!");
    }
}
