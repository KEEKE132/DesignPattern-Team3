package game.ghostVisitor;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;
import game.entities.ghosts.Pinky;

public class SuperPacgumLineVisitor implements GhostVisitor {

    @Override
    public void visit(Ghost target) {
        target.setDialogue("Run away!");
    }

    @Override
    public void visit(Blinky target) { target.setDialogue("Not the pellet!"); }

    @Override
    public void visit(Clyde target) {
        target.setDialogue("He's coming!");
    }

    @Override
    public void visit(Inky target) {
        target.setDialogue("Retreat to base!");
    }

    @Override
    public void visit(Pinky target) {
        target.setDialogue("Just a few more seconds!");
    }
}
