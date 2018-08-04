package app.menus;

import app.Platformer;
import app.constants.Constants;
import app.enums.EPauseMenuButtonType;
import app.interfaces.IDrawable;
import app.menus.panels.PauseMenuPanel;
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
    @Override
    public void setupActivateMenu() {
        // make this active
        this.isActive = true;
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()

        this.panelsList.add(new PauseMenuPanel(
            this.mainSketch,
            EPauseMenuButtonType.Continue,
            100 + this.horizontalOffset,    // add offset to account for viewbox
            100,
            Constants.PANEL_HEIGHT,
            Constants.PANEL_WIDTH,
            this.horizontalOffset,
            this.isActive
        ));

        this.panelsList.add(new PauseMenuPanel(
            this.mainSketch,
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
    @Override
    public void draw() {
        this.mainSketch.image(
            ResourceUtils.STAGE_BACKGROUND_IMAGE,
            this.horizontalOffset,  // add offset to account for viewbox
            0,
            ResourceUtils.STAGE_BACKGROUND_IMAGE.width,
            ResourceUtils.STAGE_BACKGROUND_IMAGE.height);
    }
}
