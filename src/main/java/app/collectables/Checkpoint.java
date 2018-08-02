package app.collectables;

import app.Platformer;
import app.constants.Constants;

/**
 * checkpoint
 */
public class Checkpoint extends ACollectable {

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public Checkpoint(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness, boolean isActive) {
        super(mainSketch, leftX, topY, width, height, blockLineThickness, isActive);
    }

    /**
     * display block
     */
    protected void show() {
        this.mainSketch.fill(Constants.CHECKPOINT_BLOCK_COLOR);
        this.mainSketch.strokeWeight(this.blockLineThickness);
        this.mainSketch.rect(this.leftX, this.topY, this.width, this.height);
    }

    /**
     *  check and handle contact with player
     */
    protected void checkHandleContactWithPlayer() {
        if(this.mainSketch.getCurrentActivePlayer().isActive && this.contactWithPlayer()) {   // TODO: encapsulate
            this.mainSketch.getCurrentActiveLevel().loadPlayerFromCheckPoint = true;  // TODO: encapsulate
            this.makeNotActive();
            this.mainSketch.getCurrentActiveLevelCollectables().remove(this);
        }
    }

}