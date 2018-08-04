package app.drawable.blocks;

import app.Platformer;
import app.drawable.boundaries.HorizontalBoundary;
import app.drawable.boundaries.VerticalBoundary;

/**
 * common for blocks
 */
public class ABlock {

    // main sketch
    final Platformer mainSketch;

    boolean isVisible;

    int fillColor;

    // position and dimensions
    private final int leftX;
    private final int topY;
    private final int width;
    private final int height;

    // boundaries that make up block
    HorizontalBoundary topSide;
    final HorizontalBoundary bottomSide;

    final VerticalBoundary leftSide;
    final VerticalBoundary rightSide;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    ABlock(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness,
           boolean isActive) {

        this.mainSketch = mainSketch;

        this.leftX = leftX;
        this.topY = topY;
        this.width = width;
        this.height = height;

        this.isVisible = true;

        this.bottomSide = new HorizontalBoundary(
            mainSketch,
            leftX,
            topY + height,
            width,
            blockLineThickness,
            false,
            isActive
        );

        this.leftSide = new VerticalBoundary(
            mainSketch,
            leftX,
            topY + 1,
            height - 2,
            blockLineThickness,
            isActive
        );

        this.rightSide = new VerticalBoundary(
            mainSketch,
            leftX + width,
            topY + 1,
            height - 2,
            blockLineThickness,
            isActive
        );
    }

    /**
     * set properties of this;
     * sets this to be active for all characters;
     * if given isVisible is false, only bottom boundary of block is active
     * to all characters
     */
    ABlock(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness,
           boolean isVisible, boolean isActive) {

        this.mainSketch = mainSketch;

        this.leftX = leftX;
        this.topY = topY;
        this.width = width;
        this.height = height;

        this.isVisible = isVisible;

        this.bottomSide = new HorizontalBoundary(
            mainSketch,
            leftX + 1,
            topY + height,
            width - 1,
            blockLineThickness,
            isVisible,
            false,
            isActive
        );

        this.leftSide = new VerticalBoundary(
            mainSketch,
            leftX,
            topY,
            height,
            blockLineThickness,
            isVisible,
            isActive
        );

        this.rightSide = new VerticalBoundary(
            mainSketch,
            leftX + width,
            topY,
            height,
            blockLineThickness,
            isVisible,
            isActive
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
    public void makeNotActive() {
    }
}
