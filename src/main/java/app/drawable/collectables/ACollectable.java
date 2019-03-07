package app.drawable.collectables;

import app.Platformer;
import app.drawable.characters.Player;
import app.drawable.IDrawable;

/**
 * common for rectangular collectables
 */
public abstract class ACollectable implements IDrawable {

    // main sketch
    final Platformer mainSketch;

    int fillColor;

    // position and dimensions
    final int leftX;
    final int topY;
    final int width;
    final int height;

    final int blockLineThickness;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    ACollectable(Platformer mainSketch,
                 int leftX, int topY, int width, int height,
                 int blockLineThickness, boolean isActive) {

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
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
    }

    /**
     * deactivate and remove this from game
     */
    public void makeNotActive() {
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
    }

    /**
     * display block
     */
    void show() {
        this.mainSketch.fill(this.fillColor);
        this.mainSketch.strokeWeight(this.blockLineThickness);
        this.mainSketch.rect(this.leftX, this.topY, this.width, this.height);
    }

    /**
     * check and handle contact with player;
     * to override in extended classes
     */
    abstract void checkHandleContactWithPlayer();

    /**
     * true means this contact with player
     */
    boolean contactWithPlayer() {
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