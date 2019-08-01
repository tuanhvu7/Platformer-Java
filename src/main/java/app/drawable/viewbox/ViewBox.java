package app.drawable.viewbox;

import app.Platformer;
import app.constants.Constants;
import app.drawable.characters.Player;
import app.drawable.IDrawable;
import processing.core.PVector;

/**
 * viewbox that keeps track of screen position to display character;
 * to be used in translate(x, y) in level's draw()
 */
public class ViewBox implements IDrawable {

    // main sketch
    private final Platformer mainSketch;

    // top-left (x, y) coordinates of viewbox position
    private final PVector pos;

    // velocity of viewbox
    private final PVector vel;

    /**
     * set properties of this
     */
    public ViewBox(Platformer mainSketch, int startXPos, int startYPos, boolean isActive) {
        this.mainSketch = mainSketch;
        this.pos = new PVector(startXPos, startYPos);
        this.vel = new PVector(0, 0);
        if (isActive) {
            this.makeActive();
        }
    }

    /**
     * runs continuously. handles viewbox position
     */
    @Override
    public void draw() {
        if (this.mainSketch.getCurrentActivePlayer() != null) {
            this.handleHorizontalMovement();
            this.handleVerticalMovement();
        }
    }

    /**
     * activate and add this to game
     */
    private void makeActive() {
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
    }

    /**
     * deactivate and remove this from game
     */
    public void makeNotActive() {
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
    }

    /**
     * set given value to be middle x position of this;
     * set this x position to be at start or end of level this is in if
     * given value would result this x position level overflow
     */
    public void setViewBoxHorizontalPosition(float middleXPos) {
        if (middleXPos - this.mainSketch.width / 2 < 0) {
            this.pos.x = 0;
        } else if (middleXPos + this.mainSketch.width / 2 > this.mainSketch.getCurrentActiveLevelWidth()) {
            this.pos.x = this.mainSketch.getCurrentActiveLevelWidth() - Constants.SCREEN_WIDTH;
        } else {
            this.pos.x = middleXPos - (Constants.SCREEN_WIDTH / 2);
        }
    }

    /**
     * handle horizontal movement
     */
    private void handleHorizontalMovement() {
        Player player = this.mainSketch.getCurrentActivePlayer();
        if (this.mainSketch.getCurrentActiveLevel().isHandlingLevelComplete() && this.playerAtHorizontalViewBoxBoundary(false)) {   // viewbox movement during level completion
            this.vel.x = Constants.PLAYER_LEVEL_COMPLETE_SPEED;

        } else {
            if (player.isMoveLeftPressed()) {
                if (this.pos.x > 0       // left edge of viewbox not at left edge of level
                    && this.playerAtHorizontalViewBoxBoundary(true)) {
                    this.vel.x = -Constants.PLAYER_MOVEMENT_SPEED;
                } else {
                    this.vel.x = 0;
                }
            }
            if (player.isMoveRightPressed()) {
                if (this.pos.x < this.mainSketch.getCurrentActiveLevelWidth() - this.mainSketch.width   // right edge of viewbox not at right edge of level
                    && this.playerAtHorizontalViewBoxBoundary(false)) {
                    this.vel.x = Constants.PLAYER_MOVEMENT_SPEED;
                } else {
                    this.vel.x = 0;
                }
            }
            if (!player.isMoveLeftPressed() &&
                !player.isMoveRightPressed()) {
                this.vel.x = 0;
            }
        }

        this.pos.add(this.vel.x, 0);

        // fix viewbox level boundary overflows
        if (this.pos.x > this.mainSketch.getCurrentActiveLevelWidth() - this.mainSketch.width) {
            this.pos.x = this.mainSketch.getCurrentActiveLevelWidth() - this.mainSketch.width;
        } else if (this.pos.x < 0) {
            this.pos.x = 0;
        }
    }

    /**
     * handle vertical movement
     */
    private void handleVerticalMovement() {
        Player player = this.mainSketch.getCurrentActivePlayer();

        boolean shouldScrollDown = this.playerAtVerticalViewBoxBoundary(true)
            && player.getVel().y < 0;

        boolean shouldScrollUp = this.playerAtVerticalViewBoxBoundary(false)
            && player.getVel().y > 0;
        if (shouldScrollDown || shouldScrollUp) {
            this.vel.y = player.getVel().y;
        } else {
            this.vel.y = 0;
        }

        this.pos.add(0, (int) this.vel.y);  // int cast to avoid frame rate drop

        // fix viewbox level boundary overflows
        final boolean isPlayerAtGroundLevel
            = player.getPos().y + (Constants.PLAYER_DIAMETER / 2) == Constants.LEVEL_FLOOR_Y_POSITION;
        if (this.pos.y < this.mainSketch.height - this.mainSketch.getCurrentActiveLevelHeight()) {    // top overflow
            this.pos.y = this.mainSketch.height - this.mainSketch.getCurrentActiveLevelHeight();
        } else if (this.pos.y > 0 || isPlayerAtGroundLevel) { // bottom overflow or player at ground level
            this.pos.y = 0;
        }
    }

    /**
     * return if player is at lower (left) or upper (right) boundary (depending from given value) of viewbox
     */
    private boolean playerAtHorizontalViewBoxBoundary(boolean isLowerLeftBoundary) {
        float playerXPos = this.mainSketch.getCurrentActivePlayer().getPos().x;
        if (isLowerLeftBoundary) {
            return playerXPos <= this.pos.x + Constants.HORIZONTAL_VIEWBOX_BOUNDARY * this.mainSketch.width;
        } else {
            return playerXPos >= this.pos.x + (1.00 - Constants.HORIZONTAL_VIEWBOX_BOUNDARY) * this.mainSketch.width;
        }
    }

    /**
     * return if player is at bottom or top boundary (depending on given value) of viewbox
     */
    private boolean playerAtVerticalViewBoxBoundary(boolean isBottomBoundary) {
        float playerYPos = this.mainSketch.getCurrentActivePlayer().getPos().y;
        if (isBottomBoundary) {
            return playerYPos <= this.pos.y + Constants.VERTICAL_VIEWBOX_BOUNDARY * this.mainSketch.height;
        } else {
            return playerYPos >= this.pos.y + (1.00 - Constants.VERTICAL_VIEWBOX_BOUNDARY) * this.mainSketch.height;
        }
    }

    /*** getters and setters ***/
    public PVector getPos() {
        return pos;
    }
}

