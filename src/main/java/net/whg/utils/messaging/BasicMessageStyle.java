package net.whg.utils.messaging;

import org.bukkit.ChatColor;

/**
 * The message formatting style used for common message types.
 */
public class BasicMessageStyle implements MessageFormatStyle {
    @Override
    public ChatColor getBaseColor() {
        return ChatColor.GRAY;
    }

    @Override
    public ChatColor getArgumentColor() {
        return ChatColor.DARK_AQUA;
    }

    @Override
    public ChatColor getNumberColor() {
        return ChatColor.AQUA;
    }

    @Override
    public ChatColor getBackgroundColor() {
        return ChatColor.DARK_GRAY;
    }
}
