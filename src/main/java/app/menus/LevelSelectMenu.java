package app.menus;

import app.Platformer;
import app.constants.Constants;
import app.interfaces.IDrawable;
import app.menus.panels.LevelSelectMenuPanel;
import app.utils.ResourceUtils;

/**
 * menu to select level to play;
 */
public class LevelSelectMenu extends AMenu  implements IDrawable {

    /**
     * set properties of this
     */
    public LevelSelectMenu(Platformer mainSketch, boolean isActive) {
        super(mainSketch, isActive);
    }

    /**
     * setup and activate this
     */
    public void setupActivateMenu() {
        // make this active
        this.isActive = true;
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()

        this.panelsList.add(new LevelSelectMenuPanel(
            this.mainSketch,
            1,
            100,
            100,
            Constants.PANEL_HEIGHT,
            Constants.PANEL_WIDTH,
            this.isActive
        ));

        this.panelsList.add(new LevelSelectMenuPanel(
            this.mainSketch,
            2,
            400,
            100,
            Constants.PANEL_HEIGHT,
            Constants.PANEL_WIDTH,
            this.isActive
        ));
    }

    /**
     * runs continuously; draws background of this
     */
    public void draw() {
        this.mainSketch.image(
            ResourceUtils.STAGE_BACKGROUND_IMAGE,
            0,
            0,
            ResourceUtils.STAGE_BACKGROUND_IMAGE.width,
            ResourceUtils.STAGE_BACKGROUND_IMAGE.height);
    }

}

