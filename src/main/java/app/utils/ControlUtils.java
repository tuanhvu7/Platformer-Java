package app.utils;

/**
 * Game control utils
 */
public class ControlUtils {
    private ControlUtils() {
    }

    /**
     * @return true if given key is reserved
     */
    public static boolean isKeyReserved(char keyToTest) {
        String keyStr = keyToTest + "";
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
