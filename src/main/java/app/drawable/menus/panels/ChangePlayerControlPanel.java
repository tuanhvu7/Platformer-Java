package app.drawable.menus.panels;

import app.Platformer;
import app.constants.Constants;
import app.constants.PlayerControlConstants;
import app.enums.EConfigurablePlayerControls;
import app.utils.ControlUtils;
import processing.event.KeyEvent;

import static java.nio.charset.StandardCharsets.UTF_16;

/**
 * Used to display and change player control settings
 */
public class ChangePlayerControlPanel extends APanel {
    // player control type linked to this
    private final EConfigurablePlayerControls configurablePlayerControlType;

    /**
     * set properties of this
     */
    public ChangePlayerControlPanel(Platformer mainSketch,
                                    EConfigurablePlayerControls configurableControlPanelText,
                                    int leftX, int topY, int width, int height, boolean isActive) {
        super(mainSketch, Constants.DEFAULT_PANEL_COLOR, "", leftX, topY, width, height, isActive);
        this.configurablePlayerControlType = configurableControlPanelText;
        switch (this.configurablePlayerControlType) {
            case UP:
                this.panelText = this.createFormattedPanelText(PlayerControlConstants.getPlayerUp());
                break;
            case DOWN:
                this.panelText = this.createFormattedPanelText(PlayerControlConstants.getPlayerDown());
                break;
            case LEFT:
                this.panelText = this.createFormattedPanelText(PlayerControlConstants.getPlayerLeft());
                break;
            case RIGHT:
                this.panelText = this.createFormattedPanelText(PlayerControlConstants.getPlayerRight());
                break;
            default:
                break;
        }
    }

    @Override
    void executeWhenClicked() {
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
            if (!ControlUtils.isKeyCodeReserved(keyCode)) {   // did not try to bind with reserved key
                switch (this.configurablePlayerControlType) {
                    case UP:
                        PlayerControlConstants.setPlayerUp(keyCode);
                        break;
                    case DOWN:
                        PlayerControlConstants.setPlayerDown(keyCode);
                        break;
                    case LEFT:
                        PlayerControlConstants.setPlayerLeft(keyCode);
                        break;
                    case RIGHT:
                        PlayerControlConstants.setPlayerRight(keyCode);
                        break;
                    default:
                        break;
                }
                this.panelText = this.createFormattedPanelText(keyCode);
            }

            // unselect panel after key is inputted; to avoid registerMethod again
            this.panelColor = Constants.DEFAULT_PANEL_COLOR;
            this.mainSketch.unregisterMethod("keyEvent", this); // disconnect this keyEvent() from main keyEvent()
        }
    }

    /**
     * @return formatted panel text that contains player control type and player control key
     */
    private String createFormattedPanelText(int playerControlKey) {
        String playerControlKeyStr = playerControlKey + "";
        new String(playerControlKeyStr.getBytes(UTF_16));
        return this.configurablePlayerControlType.toString() + ": " + (char) playerControlKey;
    }
}
