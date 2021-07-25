package net.whg.utils.exceptions;

/**
 * Thrown when a command is executed from the console that should only be
 * executed from a player.
 */
public class NoConsoleException extends CommandException {
    public NoConsoleException(String message) {
        super(message);
    }
}
