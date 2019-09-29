package app.utils;

/**
 * player control key bindings
 */
public class PlayerControlSettings {
    // default player controls
    private static int PLAYER_UP = 'w';
    private static int PLAYER_DOWN = 's';
    private static int PLAYER_LEFT = 'a';
    private static int PLAYER_RIGHT = 'd';

    /**
     * make this class "static"
     */
    private PlayerControlSettings() {
    }


    /*** getters and setters ***/
    public static int getPlayerUp() {
        return PLAYER_UP;
    }

    /**
     * set player up control to given keyCode if give keyCode is available
     */
    public static void setPlayerUp(int playerUp) {
        if (isKeyCodeAvailable(playerUp)) {
            PLAYER_UP = playerUp;
        }
    }

    public static int getPlayerDown() {
        return PLAYER_DOWN;
    }

    /**
     * set player down control to given keyCode if give keyCode is available
     */
    public static void setPlayerDown(int playerDown) {
        if (isKeyCodeAvailable(playerDown)) {
            PLAYER_DOWN = playerDown;
        }
    }

    public static int getPlayerLeft() {
        return PLAYER_LEFT;
    }

    /**
     * set player left control to given keyCode if give keyCode is available
     */
    public static void setPlayerLeft(int playerLeft) {
        if (isKeyCodeAvailable(playerLeft)) {
            PLAYER_LEFT = playerLeft;
        }
    }

    public static int getPlayerRight() {
        return PLAYER_RIGHT;
    }

    /**
     * set player right control to given keyCode if give keyCode is available
     */
    public static void setPlayerRight(int playerRight) {
        if (isKeyCodeAvailable(playerRight)) {
            PLAYER_RIGHT = playerRight;
        }
    }


    /**
     * @return true if given keyCode is available (not used)
     */
    public static boolean isKeyCodeAvailable(int keyCode) {
        return PlayerControlSettings.getPlayerUp() != keyCode
            && PlayerControlSettings.getPlayerLeft() != keyCode
            && PlayerControlSettings.getPlayerDown() != keyCode
            && PlayerControlSettings.getPlayerRight() != keyCode;
    }
}
