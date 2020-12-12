package app.drawable.collectables;

import app.Platformer;
import app.constants.Constants;

/**
 * level goal
 */
public class LevelGoal extends ACollectable {
    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public LevelGoal(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness, boolean initAsActive) {
        super(mainSketch, leftX, topY, width, height, blockLineThickness, initAsActive);
        this.fillColor = Constants.LEVEL_GOAL_COLOR;
    }

    /**
     * check and handle contact with player
     */
    @Override
    void checkHandleContactWithPlayer() {
        if (this.contactWithPlayer()) {
            this.makeNotActive();
            this.mainSketch.getCurrentActiveLevelDrawableCollection().removeDrawable(this);
            this.mainSketch.handleLevelComplete();
        }
    }
}
