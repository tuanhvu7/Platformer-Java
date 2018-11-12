package app.drawable.boundaries;

import app.Platformer;
import app.drawable.characters.ACharacter;
import app.drawable.characters.Player;
import app.drawable.IDrawable;

/**
 * vertical line boundaries; walls
 */
public class VerticalBoundary extends ABoundary implements IDrawable, IBoundary {
    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public VerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                            boolean isActive) {
        super(mainSketch, startXPoint, startYPoint, 0, y2Offset, boundaryLineThickness,
            true, true, true, isActive);
    }

    /**
     * set properties of this
     * sets this to affect all characters
     */
    public VerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                            boolean isVisible, boolean isActive) {
        super(mainSketch, startXPoint, startYPoint, 0, y2Offset, boundaryLineThickness,
            isVisible, true, true, isActive);
    }

    /**
     * set properties of this
     */
    public VerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                            boolean isVisible, boolean doesAffectPlayer, boolean doesAffectNonPlayers,
                            boolean isActive) {
        super(mainSketch, startXPoint, startYPoint, 0, y2Offset, boundaryLineThickness,
            isVisible, doesAffectPlayer, doesAffectNonPlayers, isActive);
    }

    /**
     * return true if collide with given character
     */
    @Override
    public boolean contactWithCharacter(ACharacter character) {
        return
            character.getPos().x + (character.getDiameter() / 2) >= this.startPoint.x         // contact right of character
                && character.getPos().x - (character.getDiameter() / 2) <= this.startPoint.x      // contact left of character
                && character.getPos().y > this.startPoint.y - (character.getDiameter() / 2)       // > lower y boundary
                && character.getPos().y < this.endPoint.y + (character.getDiameter() / 2);         // < upper y boundary
    }

    /**
     * runs continuously. checks and handles contact between this and characters
     */
    public void draw() {
        this.show();
        this.checkHandleContactWithPlayer();
        this.checkHandleContactWithNonPlayerCharacters();
    }

    /**
     * check and handle contact with player
     */
    void checkHandleContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();

        if (this.doesAffectPlayer && curPlayer.isActive()) {
            // boundary collision for player
            if (contactWithCharacter(curPlayer)) {  // this has contact with non-player
                if (!this.charactersTouchingThis.contains(curPlayer)) {  // new collision detected
                    curPlayer.changeNumberOfVerticalBoundaryContacts(1);
                    this.charactersTouchingThis.add(curPlayer);
                }
                curPlayer.handleContactWithVerticalBoundary(this.startPoint.x);

            } else {    // this DOES NOT have contact with player
                if (this.charactersTouchingThis.contains(curPlayer)) {
                    curPlayer.setAbleToMoveRight(true);
                    curPlayer.setAbleToMoveLeft(true);
                    curPlayer.changeNumberOfVerticalBoundaryContacts(-1);
                    this.charactersTouchingThis.remove(curPlayer);
                }
            }
        }
    }

    /**
     * check and handle contact with non-player characters
     */
    private void checkHandleContactWithNonPlayerCharacters() {
        if (this.doesAffectNonPlayers) {
            // boundary collision for non-player characters
            for (ACharacter curCharacter : this.mainSketch.getCurrentActiveCharactersList()) {
                if (curCharacter.isActive() && this.contactWithCharacter(curCharacter)) {
                    curCharacter.handleContactWithVerticalBoundary(this.startPoint.x);
                }
            }
        }
    }
}
