package app.drawable.boundaries;

import app.Platformer;
import app.drawable.characters.Player;
import processing.core.PVector;

/**
 * horizontal line boundaries that trigger events
 */
public class EventTriggerHorizontalBoundary extends HorizontalBoundary {
    // top boundary of event block that this is part of
    private final EventBlockTopBoundary eventBlockTopBoundary;

    // if not null, end location of warp event; else, launch event
    private final PVector endWarpPosition;

    // set character vel to this on character contact with this
    private final int launchEventVerticalVelocity;

    /**
     * set properties of this;
     * sets this to have launch event and affect all characters and be invisible
     */
    public EventTriggerHorizontalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int x2Offset,
                                          int boundaryLineThickness, int launchEventVerticalVelocity,
                                          boolean isFloorBoundary, boolean initAsActive,
                                          EventBlockTopBoundary eventBlockTopBoundary) {
        super(mainSketch, startXPoint, startYPoint, x2Offset, boundaryLineThickness,
            false, isFloorBoundary, initAsActive);

        this.endWarpPosition = null;
        this.isFloorBoundary = isFloorBoundary;
        this.eventBlockTopBoundary = eventBlockTopBoundary;
        this.launchEventVerticalVelocity = launchEventVerticalVelocity;
    }

    /**
     * set properties of this;
     * sets this to have warp event and affect all characters and be invisible
     */
    public EventTriggerHorizontalBoundary(Platformer mainSketch, int startXPoint, int startYPoint, int x2Offset, int boundaryLineThickness,
                                          int endWarpXPosition, int endWarpYPosition,
                                          boolean isFloorBoundary, boolean initAsActive,
                                          EventBlockTopBoundary eventBlockTopBoundary) {
        super(mainSketch, startXPoint, startYPoint, x2Offset, boundaryLineThickness,
            false, isFloorBoundary, initAsActive);

        this.endWarpPosition = new PVector(endWarpXPosition, endWarpYPosition);
        this.isFloorBoundary = isFloorBoundary;
        this.eventBlockTopBoundary = eventBlockTopBoundary;
        this.launchEventVerticalVelocity = 0;   // this value is not used for warp events
    }

    /**
     * check and handle contact with player
     */
    @Override
    void checkHandleContactWithPlayer() {
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();

        if (this.doesAffectPlayer) {
            // boundary collision for player
            if (this.contactWithCharacter(curPlayer)) { // this has contact with player
                curPlayer.handleContactWithEventBoundary(eventBlockTopBoundary, launchEventVerticalVelocity, endWarpPosition);
            }
        }
    }
}
