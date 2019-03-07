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

        int leftXPanelPosition = 100;
        int topYPanelPosition = 100;
        for (int i = 1; i < Constants.LEVELS_HEIGHT_ARRAY.length; i++) {
            if (leftXPanelPosition + Constants.PANEL_SIZE > ResourceUtils.LEVEL_BACKGROUND_IMAGE.width) {
                leftXPanelPosition = 100;
                topYPanelPosition += (100 + Constants.PANEL_SIZE);
            }

            this.panelsList.add(new LevelSelectMenuPanel(
                this.mainSketch,
                i,
                leftXPanelPosition,
                topYPanelPosition,
                Constants.PANEL_SIZE,
                Constants.PANEL_SIZE,
                this.isActive
            ));

            leftXPanelPosition += Constants.PANEL_SIZE + 100;
        }

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

