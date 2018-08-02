package app.menus;

import app.Platformer;
import app.constants.Constants;
import app.enums.EPauseMenuButtonType;
import app.interfaces.IDrawable;
import app.utils.ResourceUtils;

/**
 * pause menu
 */
public class PauseMenu extends AMenu implements IDrawable {

    /**
     * set properties of this
     */
    public PauseMenu(Platformer mainSketch, int horizontalOffset, boolean isActive) {
        super(mainSketch, horizontalOffset, isActive);
    }

    /**
     * setup and activate this
     */
    public void setupActivateMenu() {
        // make this active
        this.isActive = true;
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()

        this.panelsList.add(new PauseMenuPanel(
            EPauseMenuButtonType.Continue,
            100 + this.horizontalOffset,    // add offset to account for viewbox
            100,
            Constants.PANEL_HEIGHT,
            Constants.PANEL_WIDTH,
            this.horizontalOffset,
            this.isActive
        ));

        this.panelsList.add(new PauseMenuPanel(
            EPauseMenuButtonType.Quit,
            400 + this.horizontalOffset,    // add offset to account for viewbox
            100,
            Constants.PANEL_HEIGHT,
            Constants.PANEL_WIDTH,
            this.horizontalOffset,
            this.isActive
        ));
    }

    /**
     * runs continuously; draws background of this
     */
    public void draw() {
        this.mainSketch.image(
            ResourceUtils.STAGE_BACKGROUND,
            this.horizontalOffset,  // add offset to account for viewbox
            0,
            ResourceUtils.STAGE_BACKGROUND.width,
            ResourceUtils.STAGE_BACKGROUND.height);
    }
}
