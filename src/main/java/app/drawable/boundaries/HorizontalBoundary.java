package app.drawable.boundaries;

import app.Platformer;
import app.drawable.characters.ACharacter;
import app.drawable.characters.Player;

/**
 * horizontal line boundaries; floors or ceilings
 */
public class HorizontalBoundary extends ABoundary {
    // true means character cannot go through top side of boundary
    // false means character cannot go through bottom side of boundary
    boolean isFloorBoundary;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public HorizontalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int x2Offset, int boundaryLineThickness,
                              boolean isFloorBoundary, boolean isActive) {
        super(mainSketch, startXPoint, startYPoint, x2Offset, 0, boundaryLineThickness,
            true, true, true, isActive);

        this.isFloorBoundary = isFloorBoundary;
    }

    /**
     * set properties of this
     * sets this to affect all characters
     */
    public HorizontalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int x2Offset, int boundaryLineThickness,
                              boolean isVisible, boolean isFloorBoundary,
                              boolean isActive) {
        super(mainSketch, startXPoint, startYPoint, x2Offset, 0, boundaryLineThickness,
            isVisible, true, true, isActive);

        this.isFloorBoundary = isFloorBoundary;
    }

    /**
     * set properties of this
     */
    public HorizontalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int x2Offset, int boundaryLineThickness,
                              boolean isVisible, boolean doesAffectPlayer, boolean doesAffectNonPlayers,
                              boolean isFloorBoundary, boolean isActive) {
        super(mainSketch, startXPoint, startYPoint, x2Offset, 0, boundaryLineThickness,
            isVisible, doesAffectPlayer, doesAffectNonPlayers, isActive);

        this.isFloorBoundary = isFloorBoundary;
    }

    /**
     * return true if valid collision with given character
     */
    public boolean contactWithCharacter(ACharacter character) {

//        boolean characterWithinXRange =
//            character.getPos().x > this.startPoint.x - (character.getDiameter() / 2)      // > lower x boundary
//                && character.getPos().x < this.endPoint.x + (character.getDiameter() / 2);     // < upper x boundary
//
//        if (this.isFloorBoundary && character.getVel().y > 0) {
//            return
//                characterWithinXRange
//                    && character.getPos().y < this.startPoint.y                              // center of character above boundary
//                    && character.getPos().y + (character.getDiameter() / 2) >= this.startPoint.y; // bottom of character 'touching' boundary
//
//        } else if (!this.isFloorBoundary && character.getVel().y < 0) {
//            return
//                characterWithinXRange
//                    && character.getPos().y > this.startPoint.y                              // center of character below boundary
//                    && character.getPos().y - (character.getDiameter() / 2) <= this.startPoint.y; // top of character 'touching' boundary
//        } else {
//            return false;
//        }

        boolean characterWithinXRange =
            character.getPos().x > this.startPoint.x - (character.getDiameter() / 2)      // > lower x boundary
                && character.getPos().x < this.endPoint.x + (character.getDiameter() / 2);     // < upper x boundary

        boolean alreadyCharacterContact =
            character.getVel().y == 0
                && this.charactersTouchingThis.contains(character);

        if (alreadyCharacterContact) {
            return characterWithinXRange;
        }

        boolean validBoundaryContactVelocity =
            this.isFloorBoundary && character.getVel().y > 0 || !this.isFloorBoundary && character.getVel().y < 0;


        if (validBoundaryContactVelocity) {
            return
                characterWithinXRange
                    && character.getPos().y - (character.getDiameter() / 2) <= this.startPoint.y  // top of character contact or in vicinity
                    && character.getPos().y + (character.getDiameter() / 2) >= this.startPoint.y; // bottom of character contact or in vicinity
        } else {
            return false;
        }
    }

    /**
     * check and handle contact with player
     */
    void checkHandleContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();

        // boundary collision for player
        if (this.contactWithCharacter(curPlayer) && !this.isPreviousContactWithPlayer()) { // this has contact with player
            if (this.doesAffectPlayer) {
                if (!this.charactersTouchingThis.contains(curPlayer)) { // new collision detected
                    this.charactersTouchingThis.add(curPlayer);
                    if (this.isFloorBoundary) {
                        curPlayer.changeNumberOfFloorBoundaryContacts(1);
                    } else {
                        curPlayer.changeNumberOfCeilingBoundaryContacts(1);
                    }
                }
                curPlayer.handleContactWithHorizontalBoundary(this.startPoint.y, this.isFloorBoundary);
            } else {    // does NOT affect player
                this.setVisible(false);
            }

        } else {    // this DOES NOT have contact with player
            if (this.charactersTouchingThis.contains(curPlayer)) {
                if (this.isFloorBoundary) {
                    if (curPlayer.isShouldSetPreviousFloorBoundaryContact()) {
                        curPlayer.setPreviousFloorBoundaryContact(this);
                    }
                    curPlayer.changeNumberOfFloorBoundaryContacts(-1);
                } else {
                    curPlayer.changeNumberOfCeilingBoundaryContacts(-1);
                }
                this.charactersTouchingThis.remove(curPlayer);
            }
        }

    }

    /**
     * check and handle contact with non-player characters
     */
    void checkHandleContactWithNonPlayerCharacters() {
        if (this.doesAffectNonPlayers) {
            // boundary collision for non-player characters
            for (ACharacter curCharacter : this.mainSketch.getCurrentActiveCharactersList()) { // this has contact with non-player
                if (this.contactWithCharacter(curCharacter)) {
                    if (this.isFloorBoundary && !this.charactersTouchingThis.contains(curCharacter)) { // new collision detected
                        curCharacter.changeNumberOfFloorBoundaryContacts(1);
                        this.charactersTouchingThis.add(curCharacter);
                    }
                    curCharacter.handleContactWithHorizontalBoundary(this.startPoint.y, this.isFloorBoundary);

                } else {    // this DOES NOT have contact with non-player
                    if (this.isFloorBoundary && this.charactersTouchingThis.contains(curCharacter)) { // curCharacter no longer colliding with this
                        curCharacter.changeNumberOfFloorBoundaryContacts(-1);
                        this.charactersTouchingThis.remove(curCharacter);
                    }
                }
            }
        }
    }

    /**
     * return if this is previous contact with player
     */
    boolean isPreviousContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();
        return
            curPlayer.getPreviousFloorBoundaryContact() != null
                && curPlayer.getPreviousFloorBoundaryContact().equals(this);
    }
}
