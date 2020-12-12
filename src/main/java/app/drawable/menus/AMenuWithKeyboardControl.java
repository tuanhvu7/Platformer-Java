package app.drawable.menus;

import app.Platformer;
import app.drawable.IKeyControllable;
import app.drawable.menus.panels.APanel;
import app.enums.EProcessingMethods;

/**
 * common for menus with keyboard controls
 */
public abstract class AMenuWithKeyboardControl extends AMenu implements IKeyControllable {
    AMenuWithKeyboardControl(Platformer mainSketch, boolean initAsActive) {
        super(mainSketch, initAsActive);
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
        this.mainSketch.unregisterMethod(EProcessingMethods.DRAW.toString(), this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod(EProcessingMethods.KEY_EVENT.toString(), this); // disconnect this keyEvent() from main keyEvent()
    }
}
