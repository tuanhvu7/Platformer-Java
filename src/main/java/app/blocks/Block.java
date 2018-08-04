package app.blocks;

import app.Platformer;
import app.boundaries.HorizontalBoundary;
import app.constants.Constants;
import app.enums.ESongType;
import app.interfaces.IDrawable;
import app.utils.ResourceUtils;

/**
 * Block;
 * invisible block only has bottom boundary active
 */
public class Block extends ABlock implements IDrawable {

    // true means breakable from bottom
    private boolean isBreakableFromBottom;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public Block(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness, boolean isBreakableFromBottom,
                 boolean isActive) {

        super(mainSketch, leftX, topY, width, height, blockLineThickness, false);   // initially not active, to be set in makeActive()

        this.isBreakableFromBottom = isBreakableFromBottom;

        this.topSide = new HorizontalBoundary(
            this.mainSketch,
            leftX,
            topY,
            width,
            blockLineThickness,
            true,
            false  // initially not active, to be set in makeActive()
        );

        if (isActive) {
            this.makeActive();
        }
    }

    /**
     * set properties of this;
     * sets this to be active for all characters;
     * if givien isVisible is false, only bottom boundary of block is active
     * to all characters
     */
    public Block(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness,
                 boolean isVisible, boolean isBreakableFromBottom, boolean isActive) {

        super(mainSketch, leftX, topY, width, height, blockLineThickness,
            isVisible, false);  // initially not active, to be set in makeActive(), isVisible

        this.isBreakableFromBottom = isBreakableFromBottom;

        this.topSide = new HorizontalBoundary(
            this.mainSketch,
            leftX,
            topY,
            width,
            blockLineThickness,
            isVisible,
            true,
            false  // initially not active, to be set in makeActive()
        );

        if (isActive) {
            this.makeActive();
        }
    }

    /**
     * runs continuously
     */
    @Override
    public void draw() {
        if (this.isVisible) {
            this.show();
        }

        // handle player collision with invisible block
        if (this.bottomSide.contactWithCharacter(this.mainSketch.getCurrentActivePlayer())) {
            if (!this.isVisible) {
                this.handleInvisibleBlockCollisionWithPlayer();

            } else if (this.isBreakableFromBottom) {
                this.removeBlockFromPlayerContact();
            }
        }
    }


    /**
     * active and add this to game
     */
    private void makeActive() {
        this.isActive = true;
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()

        // make horizontal boundaries first since their detection takes precedence
        this.bottomSide.makeActive();

        if (this.isVisible) {
            this.topSide.makeActive();
            this.leftSide.makeActive();
            this.rightSide.makeActive();
        }
    }

    /**
     * deactivate and remove this from game
     */
    @Override
    public void makeNotActive() {
        this.isActive = false;
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()

        this.topSide.makeNotActive();
        this.bottomSide.makeNotActive();
        this.leftSide.makeNotActive();
        this.rightSide.makeNotActive();
    }

    /**
     * display block
     */
    private void show() {
        this.mainSketch.fill(Constants.DEFAULT_BLOCK_COLOR);
        this.mainSketch.rect(this.leftX, this.topY, this.width, this.height);
    }

    /**
     * handle invisible block player contact
     */
    private void handleInvisibleBlockCollisionWithPlayer() {
        if (this.isBreakableFromBottom) {
            this.removeBlockFromPlayerContact();

        } else {
            this.isVisible = true;
            this.topSide.makeActive();
            this.topSide.setVisible(true);

            this.bottomSide.setVisible(true);

            this.leftSide.makeActive();
            this.leftSide.setVisible(true);

            this.rightSide.makeActive();
            this.rightSide.setVisible(true);
        }
    }

    /**
     * remove block from player contact
     */
    private void removeBlockFromPlayerContact() {
        this.mainSketch.getCurrentActivePlayer().handleContactWithHorizontalBoundary(
            this.bottomSide.getStartPoint().y,
            false);
        ResourceUtils.playSong(ESongType.PlayerAction);
        this.makeNotActive();
        this.mainSketch.getCurrentActiveBlocksList().remove(this);
    }
}

