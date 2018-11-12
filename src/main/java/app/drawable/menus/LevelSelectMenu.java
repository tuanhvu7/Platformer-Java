package app.drawable.menus;

import app.Platformer;
import app.constants.Constants;
import app.drawable.IDrawable;
import app.drawable.menus.panels.LevelSelectMenuPanel;
import app.enums.ESongType;
import app.utils.ResourceUtils;

/**
 * menu to select level to play;
 */
public class LevelSelectMenu extends AMenu implements IDrawable {

    /**
     * set properties of this
     */
    public LevelSelectMenu(Platformer mainSketch, boolean isActive) {
        super(mainSketch, isActive);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setupActivateMenu() {
        // make this active
        this.isActive = true;
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()

        this.panelsList.add(new LevelSelectMenuPanel(
            this.mainSketch,
            1,
            100,
            100,
            Constants.PANEL_WIDTH,
            Constants.PANEL_HEIGHT,
            this.isActive
        ));

        this.panelsList.add(new LevelSelectMenuPanel(
            this.mainSketch,
            2,
            400,
            100,
            Constants.PANEL_WIDTH,
            Constants.PANEL_HEIGHT,
            this.isActive
        ));

        ResourceUtils.loopSong(ESongType.LEVEL_SELECT_MENU);
    }

    /**
     * runs continuously; draws background of this
     */
    @Override
    public void draw() {
        this.mainSketch.image(
            ResourceUtils.LEVEL_BACKGROUND_IMAGE,
            0,
            0,
            ResourceUtils.LEVEL_BACKGROUND_IMAGE.width,
            ResourceUtils.LEVEL_BACKGROUND_IMAGE.height);
    }

}

