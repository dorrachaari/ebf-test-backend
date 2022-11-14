package com.ebf.test.utils;


public class Utils {

    public static final int MAX_ITEM_ATTRIBUTE_LENGTH = 255;
    private static long lastId = 0;

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static long generateUniqueId() {
        return System.currentTimeMillis() - 1111111111111L + (++lastId);
    }

    public static boolean isAttributeSizeIncorrect(String input) {
        return input != null && input.length() > MAX_ITEM_ATTRIBUTE_LENGTH;
    }

    public static boolean isPositive(Float i) {
        return (i != null) && (i >= 0);
    }


}
