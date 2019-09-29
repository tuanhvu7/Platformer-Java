package app.utils;

public class DataTypeUtils {
    /**
     * make class 'static'
     */
    private DataTypeUtils() {
    }

    /**
     * @return int value of lowercase of given char
     */
    public static int getLowercaseNumericValue(char charToConvert) {
        return Character.toLowerCase(charToConvert);
    }
}
