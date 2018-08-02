package app.blocks;

import app.Platformer;
import app.boundaries.HorizontalBoundary;
import app.boundaries.VerticalBoundary;

/**
 * common for blocks
 */
public class ABlock {

    // main sketch
    protected Platformer mainSketch;

    protected boolean isActive;
    protected boolean isVisible;

    // position and dimensions
    protected int leftX;
    protected int topY;
    protected int width;
    protected int height;

    // boundaries that make up block
    protected HorizontalBoundary topSide;
    protected HorizontalBoundary bottomSide;

    protected VerticalBoundary leftSide;
    protected VerticalBoundary rightSide;

    /**
     * set properties of this;
     * sets this to affect all characters and be visible
     */
    public ABlock(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness,
           boolean isActive) {

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
     * if givien isVisible is false, only bottom boundary of block is active
     * to all characters
     */
    public ABlock(Platformer mainSketch, int leftX, int topY, int width, int height, int blockLineThickness,
           boolean isVisible, boolean isActive) {

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
     * deactivate and remove this from game;
     * to override in extended classes
     */
    public void makeNotActive() {}
}
