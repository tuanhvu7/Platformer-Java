package app.drawable.menus;

import app.Platformer;
import app.drawable.menus.panels.APanel;

import java.util.ArrayList;
import java.util.List;

/**
 * common for menus
 */
public abstract class AMenu {

    // main sketch
    final Platformer mainSketch;

    // horizontal offset of this from viewbox
    final int horizontalOffset;

    // list of panels in menu
    final List<APanel> panelsList;

    /**
     * set properties of this;
     * sets this if have no offset
     */
    AMenu(Platformer mainSketch, boolean isActive) {
        this.mainSketch = mainSketch;

        this.horizontalOffset = 0;
        this.panelsList = new ArrayList<>();
        if (isActive) {
            this.setupActivateMenu();
        }
    }

    /**
     * set properties of this;
     * sets this to have given offset
     */
    AMenu(Platformer mainSketch, int horizontalOffset, boolean isActive) {
        this.mainSketch = mainSketch;

        this.horizontalOffset = horizontalOffset;
        this.panelsList = new ArrayList<>();
        if (isActive) {
            this.setupActivateMenu();
        }
    }

    /**
     * activate and setup this; to override in extended classes
     */
    abstract void setupActivateMenu();

    /**
     * deactivate this
     */
    public void deactivateMenu() {
        for (APanel curPanel : this.panelsList) {
            curPanel.makeNotActive();
        }

        this.panelsList.clear();
        // make this not active
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
    }

}

