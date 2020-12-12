package app.drawable.menus;

import app.Platformer;
import app.constants.Constants;
import app.drawable.menus.panels.APanel;
import app.drawable.menus.panels.LevelSelectMenuPanel;
import app.enums.EProcessingMethods;
import app.enums.ESongType;
import app.utils.ReservedControlUtils;
import app.utils.ResourceUtils;
import processing.event.KeyEvent;

/**
 * menu to select level to play;
 */
public class LevelSelectMenu extends AMenuWithKeyboardControl {

    /**
     * set properties of this
     */
    public LevelSelectMenu(Platformer mainSketch, boolean initAsActive) {
        super(mainSketch, initAsActive);
    }

    /**
     * setup and activate this
     */
    @Override
    public void setupActivateMenu() {
        // make this active
        this.mainSketch.registerMethod(EProcessingMethods.DRAW.toString(), this); // connect this draw() from main draw()
        this.mainSketch.registerMethod(EProcessingMethods.KEY_EVENT.toString(), this); // connect this keyEvent() from main keyEvent()

        int leftXPanelPosition = 100;
        int topYPanelPosition = 100;
        for (int i = 1; i < Constants.LEVELS_HEIGHT_ARRAY.length; i++) {
            if (leftXPanelPosition + Constants.PANEL_SIZE > ResourceUtils.DEFAULT_MENU_IMAGE.width) {
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
                true
            ));

            leftXPanelPosition += Constants.PANEL_SIZE + 100;
        }

        ResourceUtils.loopSong(ESongType.OUT_OF_LEVEL_MENU);
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
    @Override
    public void keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.PRESS) {
            String keyPressed = keyEvent.getKey() + "";
            if (ReservedControlUtils.EReservedControlKeys.c.toString().equalsIgnoreCase(keyPressed)) {  // toggle checkpoint start
                for (APanel curPanel : this.panelsList) {
                    ((LevelSelectMenuPanel) curPanel).toggleLoadLevelFromCheckpoint();
                }
            } else if (ReservedControlUtils.EReservedControlKeys.u.toString().equalsIgnoreCase(keyPressed)) {   // switch to user control menu
                this.mainSketch.getLevelSelectMenu().deactivateMenu();
                this.mainSketch.getChangePlayerControlMenu().setupActivateMenu();
            }
        }
    }

}
