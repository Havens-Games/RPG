package net.whg.utils.cmdformat;

import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;

/**
 * Thrown when the command sender uses an unknown or unparsable command
 * argument.
 */
public class UnknownArgumentException extends CommandException {
    private final String argument;

    public UnknownArgumentException(String message, String argument) {
        super(message);
        this.argument = argument;
    }

    @Override
    public void printToPlayer(CommandSender sender) {
        WraithLib.log.sendMessage(sender, getMessage(), argument);
    }

    @Override
    public String toString() {
        return String.format(getMessage(), argument);
    }
}
