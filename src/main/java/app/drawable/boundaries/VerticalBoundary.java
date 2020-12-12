package app.drawable.boundaries;

import app.Platformer;
import app.drawable.characters.ACharacter;
import app.drawable.characters.ControllableEnemy;
import app.drawable.characters.Player;

/**
 * vertical line boundaries; walls
 */
public class VerticalBoundary extends ABoundary {
    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public VerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                            boolean initAsActive) {
        super(mainSketch, startXPoint, startYPoint, 0, y2Offset, boundaryLineThickness,
            true, true, true, initAsActive);
    }

    /**
     * set properties of this
     * sets this to affect all characters
     */
    public VerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                            boolean isVisible, boolean initAsActive) {
        super(mainSketch, startXPoint, startYPoint, 0, y2Offset, boundaryLineThickness,
            isVisible, true, true, initAsActive);
    }

    /**
     * set properties of this
     */
    public VerticalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int y2Offset, int boundaryLineThickness,
                            boolean isVisible, boolean doesAffectPlayer, boolean doesAffectNonPlayers,
                            boolean initAsActive) {
        super(mainSketch, startXPoint, startYPoint, 0, y2Offset, boundaryLineThickness,
            isVisible, doesAffectPlayer, doesAffectNonPlayers, initAsActive);
    }

    /**
     * return true if collide with given character
     */
    boolean contactWithCharacter(ACharacter character) {
        return
            character.getPos().x + (character.getDiameter() / 2) >= this.startPoint.x         // contact right of character
                && character.getPos().x - (character.getDiameter() / 2) <= this.startPoint.x      // contact left of character
                && character.getPos().y > this.startPoint.y - (character.getDiameter() / 2)       // > top y boundary
                && character.getPos().y < this.endPoint.y + (character.getDiameter() / 2);         // < bottom y boundary
    }

    /**
     * check and handle contact with player
     */
    void checkHandleContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();

        if (this.doesAffectPlayer) {
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
    void checkHandleContactWithNonPlayerCharacters() {
        if (this.doesAffectNonPlayers) {
            // boundary collision for non-player characters
            for (ACharacter curCharacter : this.mainSketch.getCurrentActiveLevelDrawableCollection().getCharactersList()) {
                if (this.contactWithCharacter(curCharacter)) {
                    curCharacter.handleContactWithVerticalBoundary(this.startPoint.x);

                } else if (curCharacter instanceof ControllableEnemy) { // this DOES NOT have contact with character AND character is controllable
                    ((ControllableEnemy) curCharacter).setAbleToMoveLeft(true);
                    ((ControllableEnemy) curCharacter).setAbleToMoveRight(true);
                }
            }
        }
    }
}
