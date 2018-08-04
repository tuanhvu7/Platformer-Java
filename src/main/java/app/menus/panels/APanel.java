package app.menus.panels;

import app.Platformer;
import app.constants.Constants;
import app.interfaces.IDrawable;
import processing.event.MouseEvent;

/**
 * Common for panels
 */
public abstract class APanel implements IDrawable {

    // main sketch
    protected Platformer mainSketch;

    protected int leftX;
    protected int rightX;

    protected int topY;
    protected int bottomY;

    protected int width;
    protected int height;

    private String panelText;

    // true means is displayed and clickable
    protected boolean isActive;

    /**
     * set properties of this
     */
   public  APanel(Platformer mainSketch, String panelText, int leftX, int topY, int width, int height, boolean isActive) {
       this.mainSketch = mainSketch;

       this.panelText = panelText;
        this.width = width;
        this.height = height;

        this.leftX = leftX;
        this.rightX = leftX + width;

        this.topY = topY;
        this.bottomY = topY + height;

        if(isActive) {
            this.makeActive();
        }
    }

    /**
     * active and add this to game
     */
    public void makeActive() {
        this.isActive = true;
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
        this.mainSketch.registerMethod("mouseEvent", this); // connect this mouseEvent() from main mouseEvent()
    }

    /**
     * deactivate and remove this from game
     */
    public void makeNotActive() {
        this.isActive = false;
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod("mouseEvent", this); // connect this mouseEvent() from main mouseEvent()
    }

    /**
     * runs continuously; draws rectangle panel using this properties
     */
    @Override
    public void draw() {
        this.mainSketch.fill(Constants.PANEL_COLOR);
        this.mainSketch.rect(this.leftX, this.topY, this.width, this.height);

        this.mainSketch.fill(0);
        this.mainSketch.textAlign(this.mainSketch.CENTER, this.mainSketch.CENTER);
        this.mainSketch.textSize(Constants.TEXT_SIZE);
        this.mainSketch.text(this.panelText + "", this.leftX, this.topY, this.width, this.height);
    }

    /**
     * Execute appropriate method (executeWhenClicked) when this is clicked
     */
    public void mouseEvent(MouseEvent event) {
        if(event.getAction() == MouseEvent.CLICK) {
            if(isMouseIn()) {
                executeWhenClicked();
            }
        }
    }

    /**
     * to execute when this panel is clicked; to override in extended classes
     */
    protected void executeWhenClicked() { }

    /**
     * return if mouse position inside this panel
     */
    protected boolean isMouseIn() {
        return  this.mainSketch.mouseX > this.leftX &&
            this.mainSketch.mouseX < this.rightX &&
            this.mainSketch.mouseY > this.topY &&
            this.mainSketch.mouseY < this.bottomY;
    }

}

