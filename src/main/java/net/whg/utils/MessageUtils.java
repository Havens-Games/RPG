package net.whg.utils;

import java.util.logging.Level;

import org.bukkit.Bukkit;

/**
 * A collection of utility methods dealing with sending messages to the console
 * or to players.
 */
public final class MessageUtils {
    private MessageUtils() {
    }

    /**
     * Logs an informational message to the console.
     * 
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logInfo(String message, Object... args) {
        Bukkit.getLogger().log(Level.INFO, () -> String.format(message, args));
    }

    /**
     * Logs a warning message to the console.
     * 
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logWarn(String message, Object... args) {
        Bukkit.getLogger().log(Level.WARNING, () -> String.format(message, args));
    }

    /**
     * Logs an error message to the console.
     * 
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logError(String message, Object... args) {
        Bukkit.getLogger().log(Level.SEVERE, () -> String.format(message, args));
    }

    /**
     * Logs an error message to the console.
     * 
     * @param error   - The error that was thrown.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logError(Throwable error, String message, Object... args) {
        Bukkit.getLogger().log(Level.SEVERE, error, () -> String.format(message, args));
    }
}
