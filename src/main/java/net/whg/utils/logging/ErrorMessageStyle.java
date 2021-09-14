package net.whg.utils.logging;

import org.bukkit.ChatColor;

/**
 * The message formatting style used for error message types.
 */
public class ErrorMessageStyle implements MessageFormatStyle {
    @Override
    public ChatColor getBaseColor() {
        return ChatColor.RED;
    }

    @Override
    public ChatColor getArgumentColor() {
        return ChatColor.GOLD;
    }

    @Override
    public ChatColor getNumberColor() {
        return ChatColor.GOLD;
    }

    @Override
    public ChatColor getBackgroundColor() {
        return ChatColor.DARK_RED;
    }
}
