package app.drawable.characters;

import app.Platformer;
import app.constants.Constants;
import app.drawable.IDrawable;

/**
 * flying enemy
 */
public class FlyingEnemy extends Enemy implements IDrawable {

    // top y position this.pos.y can be at before vertical movement direction change
    private final int topY;

    // bottom y position this.pos.y can be at before vertical movement direction change
    private final int bottomY;

    /**
     * set properties of this;
     * set upper and lower Y boundaries of level
     */
    public FlyingEnemy(Platformer mainSketch, int x, int y, int diameter,
                       float horizontalVel, float verticalVel,
                       boolean isInvulnerable, boolean isVisible, boolean isActive) {
        super(mainSketch, x, y, diameter, horizontalVel, isInvulnerable, isVisible, isActive);

        this.fillColor = Constants.ENEMY_COLOR;

        this.topY = Constants.SCREEN_HEIGHT - this.mainSketch.getCurrentActiveLevelHeight() - this.diameter / 2;
        this.bottomY = Constants.SCREEN_HEIGHT + this.diameter / 2;

        this.vel.x = horizontalVel;
        this.vel.y = verticalVel;

        this.isVisible = isVisible;
    }

    /**
     * set properties of this;
     * set topY and bottomY to given values
     */
    public FlyingEnemy(Platformer mainSketch, int x, int y, int diameter,
                       float horizontalVel, float verticalVel,
                       int topY, int bottomY,
                       boolean isInvulnerable, boolean isVisible, boolean isActive) {
        super(mainSketch, x, y, diameter, horizontalVel, isInvulnerable, isVisible, isActive);

        this.fillColor = Constants.ENEMY_COLOR;

        this.topY = topY;
        this.bottomY = bottomY;

        this.vel.x = horizontalVel;
        this.vel.y = verticalVel;

        this.isVisible = isVisible;
    }

    /**
     * handle movement (position, velocity)
     */
    @Override
    void handleMovement() {
        final boolean shouldReverseFromTopLimit = this.pos.y <= this.topY && this.vel.y < 0;
        final boolean shouldReverseFromBottomLimit = this.pos.y >= this.bottomY && this.vel.y > 0;
        if (shouldReverseFromTopLimit || shouldReverseFromBottomLimit) {
            this.vel.y = -this.vel.y;
        }
        this.pos.add(this.vel);
    }
}
