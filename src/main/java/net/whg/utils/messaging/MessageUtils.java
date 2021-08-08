package net.whg.utils.messaging;

import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * A collection of utility methods dealing with sending messages to the console
 * or to players.
 */
public final class MessageUtils {
    private MessageUtils() {
    }

    public static final MessageFormatStyle INFO_MESSAGE_STYLE = new BasicMessageStyle();
    public static final MessageFormatStyle WARNING_MESSAGE_STYLE = new WarningMessageStyle();
    public static final MessageFormatStyle ERROR_MESSAGE_STYLE = new ErrorMessageStyle();

    /**
     * Applies the chat color formatting to the given message.
     * 
     * @param style   - The message formatting style.
     * @param message - The message to modify.
     * @param args    - The message argument variables.
     * @return The formatted message.
     */
    public static String formatMessage(MessageFormatStyle style, String message, Object... args) {
        var formatted = new Object[args.length];

        for (var i = 0; i < formatted.length; i++) {
            if (args[i]instanceof Object[] arr) {
                formatted[i] = formatArray(arr, style) + style.getBaseColor();
                continue;
            }

            if (args[i] instanceof Double)
                formatted[i] = style.getNumberColor() + args[i].toString() + style.getBaseColor();
            else
                formatted[i] = style.getArgumentColor() + args[i].toString() + style.getBaseColor();
        }

        message = String.format(message, formatted);
        return style.getBaseColor() + message;
    }

    /**
     * Formats an object array into a color-coded object list string.
     * 
     * @param array - The array to format.
     * @param style - The message formatting style.
     * @return The color-coded list string.
     */
    private static String formatArray(Object[] array, MessageFormatStyle style) {
        var str = style.getArgumentColor().toString();

        var first = true;
        for (var elem : array) {
            if (!first)
                str += style.getBaseColor() + ", " + style.getArgumentColor();

            str += elem;
            first = false;
        }

        return str;
    }

    /**
     * Logs an informational message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logInfo(Plugin plugin, String message, Object... args) {
        plugin.getLogger().log(Level.INFO, () -> formatMessage(INFO_MESSAGE_STYLE, message, args));
    }

    /**
     * Logs a warning message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logWarn(Plugin plugin, String message, Object... args) {
        plugin.getLogger().log(Level.WARNING, () -> formatMessage(WARNING_MESSAGE_STYLE, message, args));
    }

    /**
     * Logs an error message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public static void logError(Plugin plugin, String message, Object... args) {
        plugin.getLogger().log(Level.SEVERE, () -> formatMessage(ERROR_MESSAGE_STYLE, message, args));
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
        plugin.getLogger().log(Level.SEVERE, error, () -> formatMessage(ERROR_MESSAGE_STYLE, message, args));
    }

    /**
     * Sends a message to a command sender, applying the basic informational message
     * formatting.
     * 
     * @param sender  - The command sender to send the message to.
     * @param message - The message to send.
     * @param args    - The message format arguments.
     */
    public static void sendMessage(CommandSender sender, String message, Object... args) {
        message = formatMessage(INFO_MESSAGE_STYLE, message, args);
        sender.sendMessage(message);
    }

    /**
     * Sends a warning message to a command sender, applying the warning message
     * formatting.
     * 
     * @param sender  - The command sender to send the message to.
     * @param message - The message to send.
     * @param args    - The message format arguments.
     */
    public static void sendWarning(CommandSender sender, String message, Object... args) {
        message = formatMessage(WARNING_MESSAGE_STYLE, message, args);
        sender.sendMessage(message);
    }

    /**
     * Sends an error message to a command sender, applying the error message
     * formatting.
     * 
     * @param sender  - The command sender to send the message to.
     * @param message - The message to send.
     * @param args    - The message format arguments.
     */
    public static void sendError(CommandSender sender, String message, Object... args) {
        message = formatMessage(ERROR_MESSAGE_STYLE, message, args);
        sender.sendMessage(message);
    }
}
