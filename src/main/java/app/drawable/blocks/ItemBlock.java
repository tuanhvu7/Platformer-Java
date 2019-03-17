package app.drawable.blocks;

import app.Platformer;
import app.constants.Constants;
import app.drawable.characters.Player;
import app.drawable.collectables.ACollectable;

/**
 * Block with item that appears when hit from below by player
 */
public class ItemBlock extends Block {

    // item to appear when this is hit from below by player;
    // this overrides item position to be above this and item to be not active
    private final ACollectable item;

    private boolean itemAppeared;

    private String blockText;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public ItemBlock(Platformer mainSketch, int leftX, int topY,
                     int width, int height, ACollectable item,
                     int blockLineThickness, boolean isBreakableFromBottom, boolean isActive) {

        super(mainSketch, leftX, topY, width, height,
            blockLineThickness, isBreakableFromBottom, isActive);   // initially not active, to be set in makeActive()

        this.blockText = "?";
        this.itemAppeared = false;

        this.item = item;
        this.item.makeNotActive();
        this.item.setLeftX((this.leftX + this.width / 2) - this.item.getWidth() / 2);
        this.item.setTopY(this.topY - this.item.getHeight());
    }

    /**
     * set properties of this;
     * sets this to be active for all characters;
     * if given isVisible is false, only bottom boundary of block is active
     * to all characters
     */
    public ItemBlock(Platformer mainSketch, int leftX, int topY,
                     int width, int height, ACollectable item,
                     int blockLineThickness, boolean isVisible, boolean isBreakableFromBottom, boolean isActive) {

        super(mainSketch, leftX, topY, width, height, blockLineThickness,
            isVisible, isBreakableFromBottom, isActive);  // initially not active, to be set in makeActive(), isVisible

        this.itemAppeared = false;

        this.item = item;
        this.item.makeNotActive();
        this.item.setLeftX((this.leftX + this.width / 2) - this.item.getWidth() / 2);
        this.item.setTopY(this.topY - this.item.getHeight());
    }

    /**
     * runs continuously
     */
    @Override
    public void draw() {
        if (this.isVisible) {
            this.show();
        }

        Player player = this.mainSketch.getCurrentActivePlayer();
        // handle player collision with invisible block
        if (player != null && this.bottomSide.contactWithCharacter(player)) {
            
            if (!this.itemAppeared) {
                this.item.makeActive();
                this.itemAppeared = true;
                this.blockText = "''";
            }

            if (!this.isVisible) {
                this.handleInvisibleBlockCollisionWithPlayer();

            } else if (this.isBreakableFromBottom) {
                this.removeBlockFromPlayerContact();
            }
        }
    }

    @Override
    void show() {
        this.mainSketch.fill(this.fillColor);
        this.mainSketch.rect(this.leftX, this.topY, this.width, this.height);

        this.mainSketch.fill(Constants.ITEM_BLOCK_TEXT_COLOR);
        this.mainSketch.textAlign(this.mainSketch.CENTER, this.mainSketch.CENTER);
        this.mainSketch.textSize(Math.min(this.width / 2, this.height / 2));
        this.mainSketch.text(
            this.blockText,
            this.leftX, this.topY,
            this.width, this.height);
    }

    /**
     * active and add this to game
     */
    void makeActive() {
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()

        // make horizontal boundaries first since their detection takes precedence
        this.bottomSide.makeActive();

        if (this.isVisible) {
            this.topSide.makeActive();
            this.leftSide.makeActive();
            this.rightSide.makeActive();
        }
    }
}
