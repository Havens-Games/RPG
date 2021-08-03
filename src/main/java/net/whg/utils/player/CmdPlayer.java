package net.whg.utils.player;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * A simple wrapper for a CommandSender object that adds a few useful functions
 * that should make writing basic commands much easier and allow uniform
 * plugin-wide text formatting functionality.
 */
public class CmdPlayer {
    private final CommandSender sender;

    /**
     * Creates a new CmdPlayer.
     * 
     * @param sender - The CommandSender object to wrap around.
     */
    public CmdPlayer(CommandSender sender) {
        this.sender = sender;
    }

    /**
     * Sends a basic message to the command sender. Does not preform any formatting.
     * 
     * @param message - The message string to send.
     */
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    /**
     * Sends a list of basic messages to the command sender. Each string in the
     * array appears on a new line. Does not preform any formatting.
     * 
     * @param message - The list of messages to send.
     */
    public void sendMessage(String[] message) {
        sender.sendMessage(message);
    }

    /**
     * Sends a message to the command sender, but applies a "command confirmation"
     * message formatting. This will format all colors and arguments to match that
     * color theme. Message arguments are automatically placed inside of the message
     * string with proper colors. `%s` should be used within the message string to
     * determine where to place the message arguments are to be placed.
     * 
     * If a message argument is a number, it is formatting as number. If a message
     * argument is an array, it is formatted as an array. (Note for arrays, array
     * must be cast to an Object manually due to Java syntax)
     * 
     * @param message - The message to send.
     * @param args    - The message arguments to place within the message.
     */
    public void sendConfirmation(String message, Object... args) {
        var formatted = new Object[args.length];

        for (var i = 0; i < formatted.length; i++) {
            if (args[i]instanceof Object[] arr) {
                formatted[i] = formatArray(arr, ChatColor.GRAY, ChatColor.DARK_AQUA) + ChatColor.GRAY;
                continue;
            }

            if (args[i] instanceof Double)
                formatted[i] = ChatColor.AQUA + args[i].toString() + ChatColor.GRAY;
            else
                formatted[i] = ChatColor.DARK_AQUA + args[i].toString() + ChatColor.GRAY;
        }

        message = String.format(message, formatted);
        sender.sendMessage(ChatColor.GRAY + message);
    }

    /**
     * Formats an object array into a color-coded object list string.
     * 
     * @param array          - The array to format.
     * @param separatorColor - The color of commas between the list items.
     * @param objectColor    - The color of the list items themselves.
     * @return The color-coded list string.
     */
    private String formatArray(Object[] array, ChatColor separatorColor, ChatColor objectColor) {
        var str = objectColor.toString();

        var first = true;
        for (var elem : array) {
            if (!first)
                str += separatorColor + ", " + objectColor;

            str += elem;
            first = false;
        }

        return str;
    }

    /**
     * Sends a message to the command sender, but applies a "command error" message
     * formatting. This will format all colors and arguments to match that color
     * theme. Message arguments are automatically placed inside of the message
     * string with proper colors. `%s` should be used within the message string to
     * determine where to place the message arguments are to be placed.
     * 
     * If a message argument is an array, it is formatted as an array. (Note for
     * arrays, array must be cast to an Object manually due to Java syntax)
     * 
     * @param message - The message to send.
     * @param args    - The message arguments to place within the message.
     */
    public void sendError(String message, Object... args) {
        var formatted = new Object[args.length];

        for (var i = 0; i < formatted.length; i++) {
            if (args[i]instanceof Object[] arr)
                formatted[i] = formatArray(arr, ChatColor.RED, ChatColor.GOLD) + ChatColor.RED;
            else
                formatted[i] = ChatColor.GOLD + args[i].toString() + ChatColor.RED;
        }

        message = String.format(message, formatted);
        sender.sendMessage(ChatColor.RED + message);
    }

    /**
     * Checks if the command sender is a player.
     * 
     * @return True if the command sender is a player. False otherwise.
     */
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    /**
     * Checks if the command sender is a server operator.
     * 
     * @return True if the command sender is a server operator. False otherwise.
     */
    public boolean isOp() {
        return sender.isOp();
    }

    /**
     * Gets the player that executed this command.
     * 
     * @return The player that executed this command, or null if the command sender
     *         is not a player.
     */
    public Player getPlayer() {
        if (sender instanceof Player p)
            return p;

        return null;
    }
}
