package app.boundaries;

import app.Platformer;
import app.characters.ACharacter;
import app.constants.Constants;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

/**
 * Common for line boundaries
 */
public abstract class ABoundary {
    // main sketch
    final Platformer mainSketch;

    // start point (smaller x and smaller y) coordinate for boundary
    final PVector startPoint;
    // end point (larger x and larger y) coordinate for boundary
    final PVector endPoint;

    // stoke thickness of boundary
    private final int boundaryLineThickness;

    // true means visible to player
    private boolean isVisible;

    // true means check and handle collision between this and player characters
    boolean doesAffectPlayer;

    // true means check and handle collision between this and non-player characters
    final boolean doesAffectNonPlayers;

    // set of all characters that are touching this
    final Set<ACharacter> charactersTouchingThis;

    /**
     * set properties of this
     *
     * @param x1Point  first x coordinate
     * @param y1Point  first y coordinate
     * @param x2Offset difference between first and second x coordinates (x2 - x1)
     * @param y2Offset difference between first and second y coordinates (y2 - y1)
     */
    ABoundary(Platformer mainSketch, int x1Point, int y1Point, int x2Offset, int y2Offset, int boundaryLineThickness,
              boolean isVisible, boolean doesAffectPlayer, boolean doesAffectNonPlayers,
              boolean isActive) {

        this.mainSketch = mainSketch;

        // set start points to be smaller of given values
        this.startPoint = new PVector(
            Math.min(x1Point, x1Point + x2Offset),
            Math.min(y1Point, y1Point + y2Offset));

        // set end points to be larger of given values
        this.endPoint = new PVector(
            Math.max(x1Point, x1Point + x2Offset),
            Math.max(y1Point, y1Point + y2Offset));

        this.boundaryLineThickness = boundaryLineThickness;

        this.isVisible = isVisible;
        this.doesAffectPlayer = doesAffectPlayer;
        this.doesAffectNonPlayers = doesAffectNonPlayers;

        this.charactersTouchingThis = new HashSet<>();

        if (isActive) {
            this.makeActive();
        }
    }

    /**
     * display line boundary
     */
    void show() {
        if (this.isVisible) {
            this.mainSketch.stroke(Constants.BOUNDARY_COLOR);
            this.mainSketch.strokeWeight(this.boundaryLineThickness);
            this.mainSketch.line(this.startPoint.x, this.startPoint.y, this.endPoint.x, this.endPoint.y);
        }
    }

    /**
     * active and add this to game
     */
    public void makeActive() {
        this.charactersTouchingThis.clear();
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
    }

    /**
     * deactivate and remove this from game
     */
    public void makeNotActive() {
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
    }

    /*** getters and setters ***/
    public PVector getStartPoint() {
        return startPoint;
    }

    public PVector getEndPoint() {
        return endPoint;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setDoesAffectPlayer(boolean doesAffectPlayer) {
        this.doesAffectPlayer = doesAffectPlayer;
    }

}
