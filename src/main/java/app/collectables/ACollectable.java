package app.collectables;

import app.Platformer;
import app.characters.Player;
import app.interfaces.IDrawable;

/**
 * common for rectangular collectables
 */
public class ACollectable implements IDrawable {

    // main sketch
    Platformer mainSketch;

    // position and dimensions
    int leftX;
    int topY;
    int width;
    int height;
    private boolean isActive;

    int blockLineThickness;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    ACollectable(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness, boolean isActive) {

        this.mainSketch = mainSketch;

        this.leftX = leftX;
        this.topY = topY;
        this.width = width;
        this.height = height;

        this.blockLineThickness = blockLineThickness;

        if (isActive) {
            this.makeActive();
        }
    }

    /**
     * runs continuously
     */
    @Override
    public void draw() {
        this.show();
        this.checkHandleContactWithPlayer();
    }

    /**
     * active and add this to game
     */
    private void makeActive() {
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
     * display collectable;
     * to override in extended classes
     */
    void show() { }

    /**
     * check and handle contact with player;
     * to override in extended classes
     */
    void checkHandleContactWithPlayer() { }

    /**
     * true means this contact with player
     */
    boolean contactWithPlayer() {
        // TODO: encapsulate
        Player curPlayer = this.mainSketch.getCurrentActivePlayer();
        boolean playerInHorizontalRange =
            (curPlayer.getPos().x + (curPlayer.getDiameter() / 2) >= this.leftX) &&
                (curPlayer.getPos().x - (curPlayer.getDiameter() / 2) <= this.leftX + this.width);

        boolean playerInVerticalRange =
            (curPlayer.getPos().y + (curPlayer.getDiameter() / 2) >= this.topY) &&
                (curPlayer.getPos().y - (curPlayer.getDiameter() / 2) <= this.topY + this.height);

        return playerInHorizontalRange && playerInVerticalRange;
    }
}