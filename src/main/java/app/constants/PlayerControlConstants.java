package app.constants;

/**
 * player control key bindings
 */
public class PlayerControlConstants {
    private static char PLAYER_UP = 'w';
    private static char PLAYER_DOWN = 's';
    private static char PLAYER_LEFT = 'a';
    private static char PLAYER_RIGHT = 'd';

    /**
     * make this class "static"
     */
    private PlayerControlConstants() {
    }


    /*** getters and setters ***/
    public static char getPlayerUp() {
        return PLAYER_UP;
    }

    public static void setPlayerUp(char playerUp) {
        PLAYER_UP = playerUp;
    }

    public static char getPlayerDown() {
        return PLAYER_DOWN;
    }

    public static void setPlayerDown(char playerDown) {
        PLAYER_DOWN = playerDown;
    }

    public static char getPlayerLeft() {
        return PLAYER_LEFT;
    }

    public static void setPlayerLeft(char playerLeft) {
        PLAYER_LEFT = playerLeft;
    }

    public static char getPlayerRight() {
        return PLAYER_RIGHT;
    }

    public static void setPlayerRight(char playerRight) {
        PLAYER_RIGHT = playerRight;
    }
}
