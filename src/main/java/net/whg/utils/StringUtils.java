package net.whg.utils;

/**
 * A collection of string handling utilities.
 */
public final class StringUtils {
    private StringUtils() {
    }

    /**
     * Splits a camel case string into a human-readable string with spaces.
     * 
     * @param s - The string to format.
     * @return The formatted string.
     */
    public static String splitCamelCase(String s) {
        return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
    }

}
