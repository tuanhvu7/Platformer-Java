package app.viewbox;

import app.Platformer;
import app.constants.Constants;
import app.interfaces.IDrawable;
import processing.core.PVector;

/**
 * viewbox that keeps track of screen position to display character;
 * to be used in translate(x, y) in draw()
 */
public class ViewBox implements IDrawable {

    // main sketch
    private final Platformer mainSketch;

    // top-left (x, y) coordinates of viewbox position
    private PVector pos;

    // velocity of viewbox
    private PVector vel;

    /**
     * set properties of this
     */
    public ViewBox(Platformer mainSketch, int startXPos, int startYPos, boolean isActive) {
        this.mainSketch = mainSketch;
        this.pos = new PVector(startXPos, startYPos);
        this.vel = new PVector(0, 0);
        if(isActive) {
            this.makeActive();
        }
    }

    /**
     * runs continuously. handles viewbox position
     */
    @Override
    public void draw() {
        this.handleMovement();

        // move viewbox as necessary
        this.mainSketch.translate(-this.pos.x, -0);
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
        if(middleXPos - this.mainSketch.width / 2 < 0) {
            this.pos.x = 0;
        } else if(middleXPos + this.mainSketch.width / 2 > this.mainSketch.getCurrentActiveLevelWidth()) {
            this.pos.x = this.mainSketch.getCurrentActiveLevelWidth() - Constants.SCREEN_WIDTH;
        } else {
            this.pos.x = middleXPos - (Constants.SCREEN_WIDTH / 2);
        }
    }

    /**
     * handle movement (position, velocity)
     */
    private void handleMovement() {
        if(this.mainSketch.getCurrentActiveLevel().isHandlingLevelComplete() && this.playerAtViewBoxBoundary(false))
        {   // viewbox movement during level completion
            this.vel.x = Constants.PLAYER_LEVEL_COMPLETE_SPEED;

        } else {
            if(this.mainSketch.getCurrentActivePlayer().isMoveLeftPressed()) {    // TODO: encapsulate
                if(this.pos.x > 0       // left edge of viewbox not at left edge of level
                    && this.playerAtViewBoxBoundary(true))
                {
                    this.vel.x = -Constants.PLAYER_RUN_SPEED;
                } else {
                    this.vel.x = 0;
                }
            }
            if(this.mainSketch.getCurrentActivePlayer().isMoveRightPressed()) {   // TODO: encapsulate
                if(this.pos.x < this.mainSketch.getCurrentActiveLevelWidth() - this.mainSketch.width   // right edge of viewbox not at right edge of level
                    && this.playerAtViewBoxBoundary(false))
                {
                    this.vel.x = Constants.PLAYER_RUN_SPEED;
                } else {
                    this.vel.x = 0;
                }
            }
            if(!this.mainSketch.getCurrentActivePlayer().isMoveLeftPressed() && // TODO: encapsulate
                !this.mainSketch.getCurrentActivePlayer().isMoveRightPressed())   // TODO: encapsulate
            {
                this.vel.x = 0;
            }
        }

        this.pos.add(this.vel);

        // fix viewbox level boundary overflows
        if(this.pos.x > this.mainSketch.getCurrentActiveLevelWidth() - this.mainSketch.width) {
            this.pos.x = this.mainSketch.getCurrentActiveLevelWidth() -this.mainSketch. width;
        } else if(this.pos.x < 0) {
            this.pos.x = 0;
        }
    }

    /**
     * return if player is at lower (left) or upper (right) boundary (from given value) of viewbox
     */
    private boolean playerAtViewBoxBoundary(boolean isLowerLeftBoundary) {
        if(isLowerLeftBoundary) {
            return this.mainSketch.getCurrentActivePlayer().getPos().x <= this.pos.x + Constants.VIEWBOX_BOUNDARY * this.mainSketch.width;  // TODO: encapsulate
        } else {
            return this.mainSketch.getCurrentActivePlayer().getPos().x >= this.pos.x + (1.00 - Constants.VIEWBOX_BOUNDARY) * this.mainSketch.width; // TODO: encapsulate
        }
    }

    /*** getters and setters ***/
    public PVector getPos() {
        return pos;
    }

    public void setPos(PVector pos) {
        this.pos = pos;
    }

    public PVector getVel() {
        return vel;
    }

    public void setVel(PVector vel) {
        this.vel = vel;
    }

}

