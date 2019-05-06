package app.drawable.characters;

import app.Platformer;
import app.constants.Constants;

/**
 * flying enemy
 */
public class FlyingEnemy extends Enemy {

    // top y position this.pos.y can be at before vertical movement direction change
    private final int topYLimit;

    // bottom y position this.pos.y can be at before vertical movement direction change
    private final int bottomYLimit;

    // false means this goes through horizontal boundaries
    private final boolean isAffectedByHorizontalBoundaries;

    // false means this goes through vertical boundaries
    private final boolean isAffectedByVerticalBoundaries;

    /**
     * set properties of this;
     * set upper and lower Y limits to boundaries of level
     */
    public FlyingEnemy(Platformer mainSketch, int x, int y, int diameter,
                       float horizontalVel, float verticalVel,
                       boolean isAffectedByHorizontalBoundaries, boolean isAffectedByVerticalBoundaries,
                       boolean isInvulnerable, boolean isVisible, boolean isActive) {
        super(mainSketch, x, y, diameter, horizontalVel, isInvulnerable, isVisible, isActive);

        this.fillColor = Constants.ENEMY_COLOR;

        this.topYLimit = Constants.SCREEN_HEIGHT - this.mainSketch.getCurrentActiveLevelHeight() + this.diameter / 2;
        this.bottomYLimit = Constants.SCREEN_HEIGHT - this.diameter / 2;

        this.vel.x = horizontalVel;
        this.vel.y = verticalVel;

        this.isAffectedByHorizontalBoundaries = isAffectedByHorizontalBoundaries;
        this.isAffectedByVerticalBoundaries = isAffectedByVerticalBoundaries;

        this.isVisible = isVisible;
    }

    /**
     * set properties of this;
     * set topYLimit and bottomYLimit to given values;
     */
    public FlyingEnemy(Platformer mainSketch, int x, int y, int diameter,
                       float horizontalVel, float verticalVel,
                       int topYLimit, int bottomYLimit,
                       boolean isAffectedByHorizontalBoundaries, boolean isAffectedByVerticalBoundaries,
                       boolean isInvulnerable, boolean isVisible, boolean isActive) {
        super(mainSketch, x, y, diameter, horizontalVel, isInvulnerable, isVisible, isActive);

        this.fillColor = Constants.ENEMY_COLOR;

        this.topYLimit = topYLimit;
        this.bottomYLimit = bottomYLimit;

        this.vel.x = horizontalVel;
        this.vel.y = verticalVel;

        this.isAffectedByHorizontalBoundaries = isAffectedByHorizontalBoundaries;
        this.isAffectedByVerticalBoundaries = isAffectedByVerticalBoundaries;

        this.isVisible = isVisible;
    }

    /**
     * handle movement (position, velocity)
     */
    @Override
    void handleMovement() {
        final boolean shouldReverseFromTopLimit = (this.pos.y <= this.topYLimit) && this.vel.y < 0;
        final boolean shouldReverseFromBottomLimit = (this.pos.y >= this.bottomYLimit) && this.vel.y > 0;
        if (shouldReverseFromTopLimit || shouldReverseFromBottomLimit) {
            this.vel.y = -this.vel.y;
        }
        this.pos.add(this.vel);
    }

    /**
     * handle contact with horizontal boundary
     */
    @Override
    public void handleContactWithHorizontalBoundary(float boundaryYPoint, boolean isFloorBoundary) {
        if (this.isAffectedByHorizontalBoundaries) {
            this.vel.y = -this.vel.y;
        }
    }

    /**
     * handle contact with vertical boundary
     */
    @Override
    public void handleContactWithVerticalBoundary(float boundaryXPoint) {
        if (this.isAffectedByVerticalBoundaries) {
            final boolean movingIntoBoundaryFromRight = this.pos.x > boundaryXPoint && this.vel.x < 0;
            final boolean movingIntoBoundaryFromLeft = this.pos.x < boundaryXPoint && this.vel.x > 0;
            if (movingIntoBoundaryFromRight || movingIntoBoundaryFromLeft) {
                this.vel.x = -this.vel.x; // move in opposite horizontal direction
            }
        }
    }
}
