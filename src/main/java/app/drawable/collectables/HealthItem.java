package app.drawable.collectables;

import app.Platformer;
import app.constants.Constants;

public class HealthItem extends ACollectable {

    private final int healthChangeAmount;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public HealthItem(Platformer mainSketch,
                      int healthChangeAmount,
                      int leftX, int topY, int width, int height,
                      int blockLineThickness, boolean initAsActive) {
        super(mainSketch, leftX, topY, width, height, blockLineThickness, initAsActive);
        this.healthChangeAmount = healthChangeAmount;
        this.fillColor = Constants.HEALTH_ITEM_COLOR;
    }

    @Override
    void show() {
        this.mainSketch.fill(this.fillColor);
        this.mainSketch.strokeWeight(this.blockLineThickness);
        this.mainSketch.rect(this.leftX, this.topY, this.width, this.height);
        if (this.healthChangeAmount > 0) {
            this.mainSketch.fill(Constants.POSITIVE_HEALTH_ITEM_TEXT_COLOR);
        } else if (this.healthChangeAmount == 0) {
            this.mainSketch.fill(Constants.ZERO_HEALTH_ITEM_TEXT_COLOR);
        } else {
            this.mainSketch.fill(Constants.NEGATIVE_HEALTH_ITEM_TEXT_COLOR);
        }
        this.mainSketch.textAlign(this.mainSketch.CENTER, this.mainSketch.CENTER);
        this.mainSketch.textSize(Math.min(this.width / 2, this.height / 2));
        this.mainSketch.text(
            Math.abs(this.healthChangeAmount) + "",
            this.leftX,
            this.topY,
            this.width,
            this.height);
    }

    @Override
    void checkHandleContactWithPlayer() {
        if (this.contactWithPlayer()) {
            this.mainSketch.getCurrentActivePlayer().changeHealth(this.healthChangeAmount);
            this.makeNotActive();
            this.mainSketch.getCurrentActiveLevelDrawableCollection().removeDrawable(this);
        }
    }
}
