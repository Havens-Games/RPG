package net.whg.utils.messaging;

import org.bukkit.ChatColor;

/**
 * The message formatting style used for warning message types.
 */
public class WarningMessageStyle implements MessageFormatStyle {
    @Override
    public ChatColor getBaseColor() {
        return ChatColor.GOLD;
    }

    @Override
    public ChatColor getArgumentColor() {
        return ChatColor.YELLOW;
    }

    @Override
    public ChatColor getNumberColor() {
        return ChatColor.YELLOW;
    }

    @Override
    public ChatColor getBackgroundColor() {
        return ChatColor.GOLD;
    }
}
