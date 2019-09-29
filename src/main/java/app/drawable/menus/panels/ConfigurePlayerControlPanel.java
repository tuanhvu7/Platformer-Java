package app.drawable.menus.panels;

import app.Platformer;
import app.constants.Constants;
import app.utils.PlayerControlSettings;
import app.enums.EConfigurablePlayerControls;
import app.utils.ReservedControlUtils;
import processing.event.KeyEvent;

/**
 * Used to display and change player control settings
 */
public class ConfigurePlayerControlPanel extends APanel {
    // player control type linked to this
    private final EConfigurablePlayerControls configurablePlayerControlType;

    /**
     * set properties of this
     */
    public ConfigurePlayerControlPanel(Platformer mainSketch,
                                    EConfigurablePlayerControls configurableControlPanelText,
                                    int leftX, int topY, int width, int height, boolean isActive) {
        super(mainSketch, Constants.DEFAULT_PANEL_COLOR, "", leftX, topY, width, height, isActive);
        this.configurablePlayerControlType = configurableControlPanelText;
        switch (this.configurablePlayerControlType) {
            case UP:
                this.panelText = this.createFormattedPanelText(PlayerControlSettings.getPlayerUp());
                break;
            case DOWN:
                this.panelText = this.createFormattedPanelText(PlayerControlSettings.getPlayerDown());
                break;
            case LEFT:
                this.panelText = this.createFormattedPanelText(PlayerControlSettings.getPlayerLeft());
                break;
            case RIGHT:
                this.panelText = this.createFormattedPanelText(PlayerControlSettings.getPlayerRight());
                break;
            default:
                break;
        }
    }

    @Override
    void executeWhenClicked() {
        this.mainSketch.getChangePlayerControlMenu().resetPanelsColorAndUnregisterKeyEvent();
        this.panelColor = Constants.ALTERNATE_PANEL_COLOR;
        this.mainSketch.registerMethod("keyEvent", this);
    }

    /**
     * deactivate and remove this from game
     */
    @Override
    public void makeNotActive() {
        this.mainSketch.unregisterMethod("draw", this); // disconnect this draw() from main draw()
        this.mainSketch.unregisterMethod("mouseEvent", this); // disconnect this mouseEvent() from main mouseEvent()
        this.mainSketch.unregisterMethod("keyEvent", this); // disconnect this keyEvent() from main keyEvent()
    }

    /**
     * handle panel keypress controls
     */
    public void keyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.PRESS) {
            int keyCode = Character.toLowerCase(keyEvent.getKeyCode());
            // check valid input is given (not a reserved or already-taken keyCode)
            if (!ReservedControlUtils.isKeyCodeReserved(keyCode) && PlayerControlSettings.isKeyCodeAvailable(keyCode)) {
                switch (this.configurablePlayerControlType) {
                    case UP:
                        PlayerControlSettings.setPlayerUp(keyCode);
                        break;
                    case DOWN:
                        PlayerControlSettings.setPlayerDown(keyCode);
                        break;
                    case LEFT:
                        PlayerControlSettings.setPlayerLeft(keyCode);
                        break;
                    case RIGHT:
                        PlayerControlSettings.setPlayerRight(keyCode);
                        break;
                    default:
                        break;
                }
                this.panelText = this.createFormattedPanelText(keyCode);
            }

            // unselect panel after key is inputted; to avoid registerMethod again
            this.resetColorAndUnregisterKeyEvent();
        }
    }

    /**
     * set this to have default panel color and unregister keyEvent
     */
    public void resetColorAndUnregisterKeyEvent() {
        this.panelColor = Constants.DEFAULT_PANEL_COLOR;
        this.mainSketch.unregisterMethod("keyEvent", this); // disconnect this keyEvent() from main keyEvent()
    }

    /**
     * @return formatted panel text that contains player control type and player control key
     */
    private String createFormattedPanelText(int playerControlKey) {
        String playerControlKeyStr = (char) playerControlKey + "";
        // to handle display of up, down, left, right arrows text
        switch (playerControlKey) {
            case java.awt.event.KeyEvent.VK_UP:
                playerControlKeyStr = "UP";
                break;
            case java.awt.event.KeyEvent.VK_DOWN:
                playerControlKeyStr = "DOWN";
                break;
            case java.awt.event.KeyEvent.VK_LEFT:
                playerControlKeyStr = "LEFT";
                break;
            case java.awt.event.KeyEvent.VK_RIGHT:
                playerControlKeyStr = "RIGHT";
                break;
        }

        return this.configurablePlayerControlType.toString() + ": " + playerControlKeyStr;
    }
}
