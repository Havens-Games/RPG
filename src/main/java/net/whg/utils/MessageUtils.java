package net.whg.utils;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

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
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logInfo(Plugin plugin, String message, Object... args) {
        plugin.getLogger().log(Level.INFO, () -> String.format(message, args));
    }

    /**
     * Logs a warning message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logWarn(Plugin plugin, String message, Object... args) {
        plugin.getLogger().log(Level.WARNING, () -> String.format(message, args));
    }

    /**
     * Logs an error message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logError(Plugin plugin, String message, Object... args) {
        plugin.getLogger().log(Level.SEVERE, () -> String.format(message, args));
    }

    /**
     * Logs an error message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param error   - The error that was thrown.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logError(Plugin plugin, Throwable error, String message, Object... args) {
        plugin.getLogger().log(Level.SEVERE, error, () -> String.format(message, args));
    }
}
