package app.drawable.boundaries;

import app.Platformer;
import app.drawable.characters.ACharacter;
import app.drawable.characters.Player;
import app.drawable.interfaces.IDrawable;

/**
 * horizontal line boundaries; floors or ceilings
 */
public class HorizontalBoundary extends ABoundary implements IDrawable, IBoundary {
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
    HorizontalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int x2Offset, int boundaryLineThickness,
                       boolean isVisible, boolean doesAffectPlayer, boolean doesAffectNonPlayers,
                       boolean isFloorBoundary, boolean isActive) {
        super(mainSketch, startXPoint, startYPoint, x2Offset, 0, boundaryLineThickness,
            isVisible, doesAffectPlayer, doesAffectNonPlayers, isActive);

        this.isFloorBoundary = isFloorBoundary;
    }

    /**
     * return true if valid collision with given character
     */
    @Override
    public boolean contactWithCharacter(ACharacter character) {

        // if(this.isFloorBoundary && character.getVel().y > 0) {
        //     return
        //         character.getPos().x > this.startPoint.x - (character.getDiameter() / 2)      // > lower x boundary
        //         && character.getPos().x < this.endPoint.x + (character.getDiameter() / 2)     // < upper x boundary
        //         && character.getPos().y < this.startPoint.y                              // center of character above boundary
        //         && character.getPos().y + (character.getDiameter() / 2) >= this.startPoint.y; // bottom of character 'touching' boundary

        // } else if(!this.isFloorBoundary && character.getVel().y < 0) {
        //     return
        //         character.getPos().x > this.startPoint.x - (character.getDiameter() / 2)      // > lower x boundary
        //         && character.getPos().x < this.endPoint.x + (character.getDiameter() / 2)     // < upper x boundary
        //         && character.getPos().y > this.startPoint.y                              // center of character below boundary
        //         && character.getPos().y - (character.getDiameter() / 2) <= this.startPoint.y; // top of character 'touching' boundary
        // } else {
        //     return false;
        // }

        boolean validBoundaryContactVelocity =
            this.isFloorBoundary && character.getVel().y > 0 || !this.isFloorBoundary && character.getVel().y < 0;

        if (validBoundaryContactVelocity) {
            return
                character.getPos().x > this.startPoint.x - (character.getDiameter() / 2)      // > lower x boundary
                    && character.getPos().x < this.endPoint.x + (character.getDiameter() / 2)     // < upper x boundary
                    && character.getPos().y - (character.getDiameter() / 2) <= this.startPoint.y  // top of character contact or in vicinity
                    && character.getPos().y + (character.getDiameter() / 2) >= this.startPoint.y; // bottom of character contact or in vicinity
        } else {
            return false;
        }
    }

    /**
     * runs continuously. checks and handles contact between this and characters
     */
    @Override
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
            if (this.contactWithCharacter(curPlayer)) { // this has contact with player
                if (!this.charactersTouchingThis.contains(curPlayer)) { // new collision detected
                    this.charactersTouchingThis.add(curPlayer);
                    if (this.isFloorBoundary) {
                        curPlayer.changeNumberOfFloorBoundaryContacts(1);
                    } else {
                        curPlayer.changeNumberOfCeilingBoundaryContacts(1);
                    }
                }
                curPlayer.handleContactWithHorizontalBoundary(this.startPoint.y, this.isFloorBoundary);

            } else {    // this DOES NOT have contact with player
                if (this.charactersTouchingThis.contains(curPlayer)) {
                    if (this.isFloorBoundary) {
                        curPlayer.changeNumberOfFloorBoundaryContacts(-1);
                    } else {
                        curPlayer.changeNumberOfCeilingBoundaryContacts(-1);
                    }
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
            for (ACharacter curCharacter : this.mainSketch.getCurrentActiveCharactersList()) { // this has contact with non-player
                if (curCharacter.isActive()) {
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
    }
}