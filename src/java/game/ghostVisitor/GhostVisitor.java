package game.ghostVisitor;

import game.entities.ghosts.Blinky;
import game.entities.ghosts.Clyde;
import game.entities.ghosts.Ghost;
import game.entities.ghosts.Inky;
import game.entities.ghosts.Pinky;

public interface GhostVisitor {
    public void visit (Ghost target);
    public void visit(Blinky target);
    public void visit (Clyde target);
    public void visit (Inky target);
    public void visit (Pinky target);

}
