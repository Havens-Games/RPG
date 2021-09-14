package net.whg.utils.cmdformat;

import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;

/**
 * Thrown whenever a command could not be properly executed.
 */
public abstract class CommandException extends Exception {
    protected CommandException(String message) {
        super(message);
    }

    public void printToPlayer(CommandSender sender) {
        WraithLib.log.sendMessage(sender, getMessage());
    }
}
