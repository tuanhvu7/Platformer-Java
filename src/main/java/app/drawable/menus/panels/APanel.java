package app.drawable.menus.panels;

import app.Platformer;
import app.constants.Constants;
import app.drawable.IDrawable;
import app.drawable.IMouseControllable;
import app.enums.EProcessingMethods;
import processing.event.MouseEvent;

/**
 * Common for panels
 */
public abstract class APanel implements IDrawable, IMouseControllable {

    // main sketch
    final Platformer mainSketch;

    final int leftX;
    final int rightX;

    final int topY;
    final int bottomY;

    int panelColor;
    String panelText;

    private final int width;
    private final int height;


    /**
     * set properties of this
     */
    APanel(Platformer mainSketch, int panelColor, String panelText, int leftX, int topY, int width, int height, boolean isActive) {
        this.mainSketch = mainSketch;

        this.panelText = panelText;
        this.panelColor = panelColor;
        this.width = width;
        this.height = height;

        this.leftX = leftX;
        this.rightX = leftX + width;

        this.topY = topY;
        this.bottomY = topY + height;

        if (isActive) {
            this.makeActive();
        }
    }

    /**
     * active and add this to game
     */
    void makeActive() {
        this.mainSketch.registerMethod(EProcessingMethods.DRAW.toString(), this); // connect this draw() from main draw()
        this.mainSketch.registerMethod(EProcessingMethods.MOUSE_EVENT.toString(), this); // connect this mouseEvent() from main mouseEvent()
    }

    /**
     * deactivate and remove this from game
     */
    public void makeNotActive() {
        this.mainSketch.unregisterMethod(EProcessingMethods.DRAW.toString(), this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod(EProcessingMethods.MOUSE_EVENT.toString(), this); // connect this mouseEvent() from main mouseEvent()
    }

    /**
     * runs continuously; draws rectangle panel using this properties
     */
    @Override
    public void draw() {
        this.mainSketch.fill(this.panelColor);
        this.mainSketch.rect(this.leftX, this.topY, this.width, this.height);

        this.mainSketch.fill(0);
        this.mainSketch.textAlign(this.mainSketch.CENTER, this.mainSketch.CENTER);
        this.mainSketch.textSize(Constants.TEXT_SIZE);
        this.mainSketch.text(this.panelText + "", this.leftX, this.topY, this.width, this.height);
    }

    /**
     * Execute appropriate method (executeWhenClicked) when this is clicked
     */
    @Override
    public void mouseEvent(MouseEvent event) {
        if (event.getAction() == MouseEvent.CLICK) {
            if (isMouseIn()) {
                executeWhenClicked();
            }
        }
    }

    /**
     * to execute when this panel is clicked; to override in extended classes
     */
    abstract void executeWhenClicked();

    /**
     * return if mouse position inside this panel
     */
    boolean isMouseIn() {
        return this.mainSketch.mouseX > this.leftX &&
            this.mainSketch.mouseX < this.rightX &&
            this.mainSketch.mouseY > this.topY &&
            this.mainSketch.mouseY < this.bottomY;
    }

}
