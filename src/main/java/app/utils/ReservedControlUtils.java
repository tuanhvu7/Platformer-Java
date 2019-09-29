package app.utils;

/**
 * For handling reserved key controls
 */
public class ReservedControlUtils {
    /**
     * to make class 'static'
     */
    private ReservedControlUtils() {
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
        c,  // used for toggle checkpoint
        u,  // used for configure player controls
        p   // used for game pause
    }
}
