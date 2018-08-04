package app.boundaries;

import app.Platformer;
import app.characters.Player;
import processing.core.PVector;

/**
 * horizontal line boundaries that trigger events
 */
public class EventTriggerHorizontalBoundary extends HorizontalBoundary {
    // top boundary of event block that this is part of
    private EventBlockTopBoundary eventBlockTopBoundary;

    // if not null, end location of warp event; else, launch event
    private PVector endWarpPosition;

    /**
     * set properties of this;
     * sets this to have launch event and affect all characters and be invisible
     */
    public EventTriggerHorizontalBoundary(Platformer mainSketch, int startXPoint, int startyPoint, int x2Offset, int boundaryLineThickness,
                                          boolean isFloorBoundary, boolean isActive,
                                          EventBlockTopBoundary eventBlockTopBoundary) {
        super(mainSketch, startXPoint, startyPoint, x2Offset, boundaryLineThickness,
            true, isFloorBoundary, isActive);

        this.endWarpPosition = null;
        this.isFloorBoundary = isFloorBoundary;
        this.eventBlockTopBoundary = eventBlockTopBoundary;
    }

    /**
     * set properties of this;
     * sets this to have warp event and affect all characters and be invisible
     */
    public EventTriggerHorizontalBoundary(Platformer mainSketch, int startXPoint, int startyPoint, int x2Offset, int boundaryLineThickness,
                                          int endWarpXPositon, int endWarpYPositon,
                                          boolean isFloorBoundary, boolean isActive,
                                          EventBlockTopBoundary eventBlockTopBoundary) {
        super(mainSketch, startXPoint, startyPoint, x2Offset, boundaryLineThickness,
            true, isFloorBoundary, isActive);

        this.endWarpPosition = new PVector(endWarpXPositon, endWarpYPositon);
        this.isFloorBoundary = isFloorBoundary;
        this.eventBlockTopBoundary = eventBlockTopBoundary;
    }

    /**
     * check and handle contact with player
     */
    @Override
    public void checkHandleContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();

        if (this.doesAffectPlayer && curPlayer.isActive()) { // TODO: encapsulate
            // boundary collision for player
            if (this.contactWithCharacter(curPlayer)) { // this has contact with player
                curPlayer.handleConactWithEventBoundary(eventBlockTopBoundary, endWarpPosition);
            }
        }
    }
}
