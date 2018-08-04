package app.drawable.characters;

import app.Platformer;
import app.constants.Constants;
import processing.core.PVector;

/**
 * Common for circular characters
 */
public abstract class ACharacter {

    // main sketch
    final Platformer mainSketch;

    // (x, y) coordinates of center of character (x, y)
    final PVector pos;
    // (x, y) velocity of character (x, y)
    PVector vel;

    final int diameter;

    int fillColor;

    // number of floor-like boundaries this is touching;
    int numberOfFloorBoundaryContacts;

    boolean isActive;

    /**
     * set properties of this
     */
    ACharacter(Platformer mainSketch, int x, int y, int diameter, boolean isActive) {
        this.mainSketch = mainSketch;
        this.pos = new PVector(x, y);
        this.vel = new PVector();
        this.diameter = diameter;

        this.isActive = true;

        this.numberOfFloorBoundaryContacts = 0;

        if (isActive) {
            this.makeActive();
        }
    }

    /**
     * draw circle character
     */
    void show() {
        this.mainSketch.fill(this.fillColor);
        this.mainSketch.strokeWeight(0);
        this.mainSketch.ellipse(this.pos.x, this.pos.y, this.diameter, this.diameter);
    }

    /**
     * handle contact with horizontal boundary
     */
    public void handleContactWithHorizontalBoundary(float boundaryYPoint, boolean isFloorBoundary) {
        if (isFloorBoundary) { // floor-like boundary
            if (this.vel.y > 0) {    // boundary only act like floor if this is falling onto boundary
                this.vel.y = 0;
                this.pos.y = boundaryYPoint - this.diameter / 2;
            }
        } else {    // ceiling-like boundary
            if (this.vel.y < 0) {    // boundary only act like ceiling if this is rising into boundary
                this.vel.y = 1;
                this.pos.add(this.vel);
            }
        }
    }

    /**
     * handle contact with vertical boundary
     */
    public void handleContactWithVerticalBoundary(float boundaryXPoint) {
        this.vel.x = -this.vel.x; // move in opposite horizontal direction
    }

    /**
     * handle arial physics
     */
    void handleInAirPhysics() {
        this.vel.y = Math.min(this.vel.y + Constants.GRAVITY.y, Constants.MAX_VERTICAL_VELOCITY);
    }


    /**
     * active and add this to game
     */
    public void makeActive() {
        this.isActive = true;
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
    }

    /**
     * deactivate and remove this from game
     */
    public void makeNotActive() {
        this.isActive = false;
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
    }

    /**
     * @param amount change numberOfFloorBoundaryContacts by given value
     */
    public void changeNumberOfFloorBoundaryContacts(int amount) {
        this.numberOfFloorBoundaryContacts += amount;
    }

    /**
     * check and handle death of this by going offscreen
     */
    void checkHandleOffscreenDeath() {
        if (this.pos.y + this.diameter / 2 <= 0 || this.pos.y - this.diameter / 2 >= this.mainSketch.height) {
            this.handleDeath(true);
        }
    }

    /**
     * handle death of this;
     * to override in extended methods
     */
    void handleDeath(boolean isOffscreenDeath) {
    }

    /*** getters and setters ***/
    public PVector getPos() {
        return pos;
    }

    public PVector getVel() {
        return vel;
    }

    public void setVel(PVector vel) {
        this.vel = vel;
    }

    public int getDiameter() {
        return diameter;
    }

    public boolean isActive() {
        return isActive;
    }
}
