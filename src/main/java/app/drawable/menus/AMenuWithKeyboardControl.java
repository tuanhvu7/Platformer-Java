package app.drawable.menus;

import app.Platformer;
import app.drawable.menus.panels.APanel;

/**
 * common for menus with keyboard controls
 */
public abstract class AMenuWithKeyboardControl extends AMenu {
    AMenuWithKeyboardControl(Platformer mainSketch, boolean isActive) {
        super(mainSketch, isActive);
    }

    /**
     * deactivate this
     */
    @Override
    public void deactivateMenu() {
        for (APanel curPanel : this.panelsList) {
            curPanel.makeNotActive();
        }

        this.panelsList.clear();
        // make this not active
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod("keyEvent", this); // disconnect this keyEvent() from main keyEvent()
    }
}
