package game.ghostVisitor;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;
import game.entities.ghosts.Pinky;

public class HouseLineVisitor implements GhostVisitor {

    @Override
    public void visit(Ghost target) {
        target.setDialogue("I'm back and ready!");
    }

    @Override
    public void visit(Blinky target) {
        target.setDialogue("Fully recovered!\nTime for revenge!");
    }

    @Override
    public void visit(Clyde target) {
        target.setDialogue("Phew! I'm feeling better now!");
    }

    @Override
    public void visit(Inky target) {
        target.setDialogue("Let me think of a new strategy...");
    }

    @Override
    public void visit(Pinky target) {
        target.setDialogue("Recovered!\nI won't miss next time!");
    }
}
