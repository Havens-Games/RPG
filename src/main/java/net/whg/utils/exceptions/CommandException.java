package net.whg.utils.exceptions;

import net.whg.utils.player.CmdPlayer;

/**
 * Thrown whenever a command could not be properly executed.
 */
public abstract class CommandException extends Exception {
    protected CommandException(String message) {
        super(message);
    }

    public void printToPlayer(CmdPlayer sender) {
        sender.sendError(getMessage());
    }
}
