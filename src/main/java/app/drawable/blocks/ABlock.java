package app.drawable.blocks;

import app.Platformer;
import app.drawable.IDrawable;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.boundaries.VerticalBoundary;

/**
 * common for blocks
 */
public abstract class ABlock implements IDrawable {

    // main sketch
    final Platformer mainSketch;

    boolean isVisible;

    int fillColor;

    // position and dimensions
    final int leftX;
    final int topY;
    final int width;
    final int height;

    // boundaries that make up block
    HorizontalBoundary topSide;
    final HorizontalBoundary bottomSide;

    final VerticalBoundary leftSide;
    final VerticalBoundary rightSide;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    ABlock(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness) {

        this.mainSketch = mainSketch;

        this.leftX = leftX;
        this.topY = topY;
        this.width = width;
        this.height = height;

        this.isVisible = true;

        // pass initAsActive=false to constructors since need this and its boundaries active state to be synced
        this.bottomSide = new HorizontalBoundary(
            mainSketch,
            leftX,
            topY + height,
            width,
            blockLineThickness,
            false,
            false
        );

        this.leftSide = new VerticalBoundary(
            mainSketch,
            leftX,
            topY + 1,
            height - 2,
            blockLineThickness,
            false
        );

        this.rightSide = new VerticalBoundary(
            mainSketch,
            leftX + width,
            topY + 1,
            height - 2,
            blockLineThickness,
            false
        );
    }

    /**
     * set properties of this;
     * sets this to be active for all characters;
     * if given isVisible is false, only bottom boundary of block is active
     * to all characters
     */
    ABlock(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness,
           boolean isVisible) {

        this.mainSketch = mainSketch;

        this.leftX = leftX;
        this.topY = topY;
        this.width = width;
        this.height = height;

        this.isVisible = isVisible;

        // pass initAsActive=false to constructors since need this and its boundaries active state to be synced
        this.bottomSide = new HorizontalBoundary(
            mainSketch,
            leftX + 1,
            topY + height,
            width - 1,
            blockLineThickness,
            isVisible,
            false,
            false
        );

        this.leftSide = new VerticalBoundary(
            mainSketch,
            leftX,
            topY,
            height,
            blockLineThickness,
            isVisible,
            false
        );

        this.rightSide = new VerticalBoundary(
            mainSketch,
            leftX + width,
            topY,
            height,
            blockLineThickness,
            isVisible,
            false
        );
    }

    /**
     * display block
     */
    void show() {
        this.mainSketch.fill(this.fillColor);
        this.mainSketch.rect(this.leftX, this.topY, this.width, this.height);
    }

    /**
     * deactivate and remove this from game;
     * to override in extended classes
     */
    public abstract void makeNotActive();
}
