package net.whg.utils.logging;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A logging wrapper that makes it easy to send messages to the console or a
 * command sender, that has been properly formatted based on the correct message
 * style and colors.
 */
public class LoggingHandler {
    private final Logger logger;

    /**
     * Creates a new LoggingHandler.
     * 
     * @param logger - The logger this handler logs to.
     */
    public LoggingHandler(Logger logger) {
        this.logger = logger;
    }

    /**
     * Logs an informational message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public void logInfo(String message, Object... args) {
        Supplier<String> s = () -> MessageUtils.formatMessage(MessageUtils.INFO_MESSAGE_STYLE, message, args);
        logger.log(Level.INFO, s);
    }

    /**
     * Logs a warning message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public void logWarn(String message, Object... args) {
        Supplier<String> s = () -> MessageUtils.formatMessage(MessageUtils.WARNING_MESSAGE_STYLE, message, args);
        logger.log(Level.WARNING, s);
    }

    /**
     * Logs an error message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public void logError(String message, Object... args) {
        Supplier<String> s = () -> MessageUtils.formatMessage(MessageUtils.ERROR_MESSAGE_STYLE, message, args);
        logger.log(Level.SEVERE, s);
    }

    /**
     * Logs an error message to the console.
     * 
     * @param plugin  - The plugin that is logging this message.
     * @param error   - The error that was thrown.
     * @param message - The message.
     * @param args    - The message format arguments.
     */
    public void logError(Throwable error, String message, Object... args) {
        Supplier<String> s = () -> MessageUtils.formatMessage(MessageUtils.ERROR_MESSAGE_STYLE, message, args);
        logger.log(Level.SEVERE, error, s);
    }

    /**
     * Sends a message to a command sender, applying the basic informational message
     * formatting.
     * 
     * @param sender  - The command sender to send the message to.
     * @param message - The message to send.
     * @param args    - The message format arguments.
     */
    public void sendMessage(CommandSender sender, String message, Object... args) {
        message = MessageUtils.formatMessage(MessageUtils.INFO_MESSAGE_STYLE, message, args);
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
    public void sendWarning(CommandSender sender, String message, Object... args) {
        message = MessageUtils.formatMessage(MessageUtils.WARNING_MESSAGE_STYLE, message, args);
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
    public void sendError(CommandSender sender, String message, Object... args) {
        message = MessageUtils.formatMessage(MessageUtils.ERROR_MESSAGE_STYLE, message, args);
        sender.sendMessage(message);
    }

    /**
     * Sends a message to a command sender, applying the basic informational message
     * formatting as well as inserting in PlaceholderAPI-driven placeholder texts
     * for the given player.
     * 
     * @param player  - The player to send to the PlaceholderAPI plugin.
     * @param sender  - The command sender to send the message to.
     * @param message - The message to send.
     * @param args    - The message format arguments.
     */
    public void sendPlaceholderMessage(CommandSender sender, Player player, String message, Object... args) {
        message = MessageUtils.formatMessageWithPlaceholder(player, MessageUtils.INFO_MESSAGE_STYLE, message, args);
        sender.sendMessage(message);
    }

    /**
     * Sends a warning message to a command sender, applying the warning message
     * formatting as well as inserting in PlaceholderAPI-driven placeholder texts
     * for the given player.
     * 
     * @param player  - The player to send to the PlaceholderAPI plugin.
     * @param sender  - The command sender to send the message to.
     * @param message - The message to send.
     * @param args    - The message format arguments.
     */
    public void sendPlaceholderWarning(CommandSender sender, Player player, String message, Object... args) {
        message = MessageUtils.formatMessageWithPlaceholder(player, MessageUtils.WARNING_MESSAGE_STYLE, message, args);
        sender.sendMessage(message);
    }

    /**
     * Sends an error message to a command sender, applying the error message
     * formatting as well as inserting in PlaceholderAPI-driven placeholder texts
     * for the given player.
     * 
     * @param player  - The player to send to the PlaceholderAPI plugin.
     * @param sender  - The command sender to send the message to.
     * @param message - The message to send.
     * @param args    - The message format arguments.
     */
    public void sendPlaceholderError(CommandSender sender, Player player, String message, Object... args) {
        message = MessageUtils.formatMessageWithPlaceholder(player, MessageUtils.ERROR_MESSAGE_STYLE, message, args);
        sender.sendMessage(message);
    }
}
