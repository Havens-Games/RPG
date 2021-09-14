package net.whg.utils.logging;

import org.bukkit.ChatColor;

/**
 * Represents a message formatting style that is used in logging of command
 * feedback.
 */
public interface MessageFormatStyle {
    /**
     * Gets the base color that is used for standard message text.
     * 
     * @return The base color.
     */
    ChatColor getBaseColor();

    /**
     * Gets the primary argument color that is used for message arguments that
     * should stand out or draw attention.
     * 
     * @return The argument color.
     */
    ChatColor getArgumentColor();

    /**
     * Gets the number color that is used for numerical arguments.
     * 
     * @return The number color.
     */
    ChatColor getNumberColor();

    /**
     * Gets the background color that is used for borders and separators in large
     * message blocks.
     * 
     * @return The background color.
     */
    ChatColor getBackgroundColor();
}
