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
        this.fillColor = Constants.CHECKPOINT_COLOR;
    }

    /**
     *  check and handle contact with player
     */
    @Override
    protected void checkHandleContactWithPlayer() {
        if(this.mainSketch.getCurrentActivePlayer().isActive() && this.contactWithPlayer()) {
            this.mainSketch.getCurrentActiveLevel().setLoadPlayerFromCheckPoint(true);
            this.makeNotActive();
            this.mainSketch.getCurrentActiveLevelCollectables().remove(this);
        }
    }

}