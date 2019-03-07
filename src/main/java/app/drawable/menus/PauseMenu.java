package app.drawable.menus;

import app.Platformer;
import app.constants.Constants;
import app.enums.EPauseMenuButtonType;
import app.drawable.IDrawable;
import app.drawable.menus.panels.PauseMenuPanel;
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
            EPauseMenuButtonType.CONTINUE,
            100 + this.horizontalOffset,    // add offset to account for viewbox
            100,
            Constants.PANEL_SIZE,
            Constants.PANEL_SIZE,
            this.horizontalOffset,
            this.isActive
        ));

        this.panelsList.add(new PauseMenuPanel(
            this.mainSketch,
            EPauseMenuButtonType.QUIT,
            400 + this.horizontalOffset,    // add offset to account for viewbox
            100,
            Constants.PANEL_SIZE,
            Constants.PANEL_SIZE,
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
            ResourceUtils.LEVEL_BACKGROUND_IMAGE,
            this.horizontalOffset,  // add offset to account for viewbox
            0,
            ResourceUtils.LEVEL_BACKGROUND_IMAGE.width,
            ResourceUtils.LEVEL_BACKGROUND_IMAGE.height);
    }
}
