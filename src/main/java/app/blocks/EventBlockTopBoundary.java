package app.blocks;

import app.Platformer;
import app.boundaries.HorizontalBoundary;
import app.characters.Player;

/**
 * top horizontal line boundaries of event blocks;
 * player can descend down this
 */
public class EventBlockTopBoundary extends HorizontalBoundary {

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public EventBlockTopBoundary(Platformer mainSketch, int startXPoint, int startyPoint, int x2Offset, int boundaryLineThickness,
                          boolean isActive) {
        super(mainSketch, startXPoint, startyPoint, x2Offset, boundaryLineThickness, true, isActive);
    }

    /**
     * set properties of this
     * sets this to affect all characters
     */
    public EventBlockTopBoundary(Platformer mainSketch, int startXPoint, int startyPoint, int x2Offset, int boundaryLineThickness,
                          boolean isVisible, boolean isActive) {
        super(mainSketch, startXPoint, startyPoint, x2Offset, boundaryLineThickness, isVisible, true, isActive);
    }

    /**
     * set properties of this
     */
    public EventBlockTopBoundary(Platformer mainSketch, int startXPoint, int startyPoint, int x2Offset, int boundaryLineThickness,
                          boolean isVisible, boolean doesAffectPlayer, boolean doesAffectNonPlayers,
                          boolean isActive) {
        super(mainSketch, startXPoint, startyPoint, x2Offset, boundaryLineThickness,
            isVisible, doesAffectPlayer, doesAffectNonPlayers,
            true, isActive);
    }

    /**
     * check and handle contact with player
     */
    public void checkHandleContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();
        if(this.doesAffectPlayer && curPlayer.isActive) { // TODO: encapsulate
            // boundary collision for player
            if(this.contactWithCharacter(curPlayer)) { // this has contact with player
                if(!curPlayer.eventTopBoundaryContacts.contains(this)) { // new collision detected    // TODO: encapsulate
                    curPlayer.eventTopBoundaryContacts.add(this);
                    curPlayer.numberOfFloorBoundaryContacts++; // TODO: encapsulate
                    curPlayer.handleContactWithHorizontalBoundary(this.startPoint.y, true);
                }

            } else {    // this DOES NOT have contact with player
                if(curPlayer.eventTopBoundaryContacts.contains(this)) {   // TODO: encapsulate
                    curPlayer.eventTopBoundaryContacts.remove(this);   // TODO: encapsulate
                    curPlayer.numberOfFloorBoundaryContacts--; // TODO: encapsulate
                }
            }
        }
    }

}
