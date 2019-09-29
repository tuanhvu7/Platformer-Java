package app.constants;

/**
 * player control key bindings
 */
public class PlayerControlConstants {
    private static int PLAYER_UP = 'w';
    private static int PLAYER_DOWN = 's';
    private static int PLAYER_LEFT = 'a';
    private static int PLAYER_RIGHT = 'd';

    /**
     * make this class "static"
     */
    private PlayerControlConstants() {
    }


    /*** getters and setters ***/
    public static int getPlayerUp() {
        return PLAYER_UP;
    }

    public static void setPlayerUp(int playerUp) {
        PLAYER_UP = playerUp;
    }

    public static int getPlayerDown() {
        return PLAYER_DOWN;
    }

    public static void setPlayerDown(int playerDown) {
        PLAYER_DOWN = playerDown;
    }

    public static int getPlayerLeft() {
        return PLAYER_LEFT;
    }

    public static void setPlayerLeft(int playerLeft) {
        PLAYER_LEFT = playerLeft;
    }

    public static int getPlayerRight() {
        return PLAYER_RIGHT;
    }

    public static void setPlayerRight(int playerRight) {
        PLAYER_RIGHT = playerRight;
    }
}
