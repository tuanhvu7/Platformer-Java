package app.menus;

import app.Platformer;
import app.menus.panels.APanel;

import java.util.ArrayList;
import java.util.List;

/**
 * common for menus
 */
public abstract class AMenu {

    // main sketch
    protected Platformer mainSketch;

    // horizontal offset of this from viewbox
    protected int horizontalOffset;

    // true means displayed
    protected boolean isActive;

    // list of panels in menu
    protected List<APanel> panelsList;

    /**
     * set properties of this;
     * sets this is have no offsetted
     */
    public AMenu(Platformer mainSketch, boolean isActive) {
        this.mainSketch = mainSketch;

        this.horizontalOffset = 0;
        this.panelsList = new ArrayList<APanel>();
        if(isActive) {
            this.setupActivateMenu();
        }
    }

    /**
     * set properties of this;
     * sets this to have given offset
     */
    public AMenu(Platformer mainSketch, int horizontalOffset, boolean isActive) {
        this.mainSketch = mainSketch;

        this.horizontalOffset = horizontalOffset;
        this.panelsList = new ArrayList<APanel>();
        if(isActive) {
            this.setupActivateMenu();
        }
    }

    /**
     * activate and setup this; to override in extended classes
     */
    public void setupActivateMenu() { }

    /**
     * deactiviate this
     */
    public void deactivateMenu() {
        for(APanel curPanel : this.panelsList) {
            curPanel.makeNotActive();
        }

        this.panelsList.clear();
        // make this not active
        this.isActive = false;
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
    }

}

