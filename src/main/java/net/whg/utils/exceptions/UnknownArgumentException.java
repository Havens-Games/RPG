package net.whg.utils.exceptions;

import net.whg.utils.player.CmdPlayer;

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
    public void printToPlayer(CmdPlayer sender) {
        sender.sendError(getMessage(), argument);
    }

    @Override
    public String toString() {
        return String.format(getMessage(), argument);
    }
}
