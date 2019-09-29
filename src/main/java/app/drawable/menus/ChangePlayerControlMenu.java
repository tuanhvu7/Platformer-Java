package app.drawable.menus;

import app.Platformer;
import app.constants.Constants;
import app.drawable.menus.panels.ChangePlayerControlPanel;
import app.enums.EConfigurablePlayerControls;
import app.enums.ESongType;
import app.utils.ResourceUtils;

/**
 * Menu to change player controls
 */
public class ChangePlayerControlMenu extends AMenu {

    /**
     * set properties of this
     */
    public ChangePlayerControlMenu(Platformer mainSketch, boolean isActive) {
        super(mainSketch, isActive);
    }

    @Override
    public void setupActivateMenu() {
        // make this active
        this.mainSketch.registerMethod("draw", this); // connect this draw() from main draw()
        int leftXPanelPosition = 100;
        int topYPanelPosition = 100;
        for (EConfigurablePlayerControls curConfigurablePlayerControls : EConfigurablePlayerControls.values()) {
            if (leftXPanelPosition + Constants.PANEL_SIZE > ResourceUtils.LEVEL_BACKGROUND_IMAGE.width) {
                leftXPanelPosition = 100;
                topYPanelPosition += (100 + Constants.PANEL_SIZE);
            }

            this.panelsList.add(new ChangePlayerControlPanel(
                this.mainSketch,
                curConfigurablePlayerControls,
                leftXPanelPosition,
                topYPanelPosition,
                Constants.PANEL_SIZE,
                Constants.PANEL_SIZE,
                true
            ));

            leftXPanelPosition += Constants.PANEL_SIZE + 100;
            ResourceUtils.loopSong(ESongType.OUT_OF_LEVEL_MENU);
        }
    }

    /**
     * runs continuously; draws background of this
     */
    @Override
    public void draw() {
        this.mainSketch.background(ResourceUtils.DEFAULT_MENU_IMAGE);
    }
}
