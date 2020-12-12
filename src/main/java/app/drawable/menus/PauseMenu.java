package app.drawable.menus;

import app.Platformer;
import app.constants.Constants;
import app.drawable.menus.panels.PauseMenuPanel;
import app.enums.EPauseMenuButtonType;
import app.enums.EProcessingMethods;

/**
 * pause menu
 */
public class PauseMenu extends AMenu {

    /**
     * set properties of this
     */
    public PauseMenu(Platformer mainSketch, int horizontalOffset, boolean initAsActive) {
        super(mainSketch, horizontalOffset, initAsActive);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setupActivateMenu() {
        // make this active
        this.mainSketch.registerMethod(EProcessingMethods.DRAW.toString(), this); // connect this draw() from main draw()

        this.panelsList.add(new PauseMenuPanel(
            this.mainSketch,
            EPauseMenuButtonType.CONTINUE,
            100 + this.horizontalOffset,    // add offset to account for viewbox
            100,
            Constants.PANEL_SIZE,
            Constants.PANEL_SIZE,
            this.horizontalOffset,
            true
        ));

        this.panelsList.add(new PauseMenuPanel(
            this.mainSketch,
            EPauseMenuButtonType.QUIT,
            400 + this.horizontalOffset,    // add offset to account for viewbox
            100,
            Constants.PANEL_SIZE,
            Constants.PANEL_SIZE,
            this.horizontalOffset,
            true
        ));
    }

    /**
     * runs continuously
     */
    @Override
    public void draw() { }
}
