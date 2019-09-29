package app.utils;

/**
 * Game control utils
 */
public class ControlUtils {
    /**
     * to make class 'static'
     */
    private ControlUtils() {
    }

    /**
     * @return true if given keycode is reserved
     */
    public static boolean isKeyCodeReserved(int keyToTest) {
        String keyStr = (char) keyToTest + "";
        try {
            EReservedControlKeys.valueOf(keyStr.toLowerCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * reserved control keys
     */
    public enum EReservedControlKeys {
        c,  // toggle checkpoint
        u,  // user controls set
        p   // game pause
    }
}
