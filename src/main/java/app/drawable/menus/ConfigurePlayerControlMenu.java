package app.drawable.menus;

import app.Platformer;
import app.constants.Constants;
import app.drawable.menus.panels.APanel;
import app.drawable.menus.panels.ConfigurePlayerControlPanel;
import app.enums.EConfigurablePlayerControls;
import app.enums.EProcessingMethods;
import app.enums.ESongType;
import app.utils.ReservedControlUtils;
import app.utils.ResourceUtils;
import processing.event.KeyEvent;

/**
 * Menu to change player controls
 */
public class ConfigurePlayerControlMenu extends AMenuWithKeyboardControl {

    /**
     * set properties of this
     */
    public ConfigurePlayerControlMenu(Platformer mainSketch, boolean isActive) {
        super(mainSketch, isActive);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setupActivateMenu() {
        // make this active
        this.mainSketch.registerMethod(EProcessingMethods.DRAW.toString(), this); // connect this draw() from main draw()
        this.mainSketch.registerMethod(EProcessingMethods.KEY_EVENT.toString(), this); // connect this draw() from main draw()
        int leftXPanelPosition = 100;
        int topYPanelPosition = 100;
        for (EConfigurablePlayerControls curConfigurablePlayerControls : EConfigurablePlayerControls.values()) {
            if (leftXPanelPosition + Constants.PANEL_SIZE > ResourceUtils.DEFAULT_MENU_IMAGE.width) {
                leftXPanelPosition = 100;
                topYPanelPosition += (100 + Constants.PANEL_SIZE);
            }

            this.panelsList.add(new ConfigurePlayerControlPanel(
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

    /**
     * handle keypress
     */
    public void keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.PRESS) {
            String keyPressed = keyEvent.getKey() + "";
            if (ReservedControlUtils.EReservedControlKeys.u.toString().equalsIgnoreCase(keyPressed)) {   // switch to level select
                this.deactivateMenu();
                this.mainSketch.getLevelSelectMenu().setupActivateMenu();
            }
        }
    }

    /**
     * reset all of this' panel colors and unregister from all of this' panel keyEvent
     */
    public void resetPanelsColorAndUnregisterKeyEvent() {
        for (APanel curPanel : this.panelsList) {
            ((ConfigurePlayerControlPanel) curPanel).resetColorAndUnregisterKeyEvent();
        }
    }
}
