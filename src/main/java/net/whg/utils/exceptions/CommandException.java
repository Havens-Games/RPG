package net.whg.utils.exceptions;

/**
 * Thrown whenever a command could not be properly executed.
 */
public abstract class CommandException extends Exception {
    protected CommandException(String message) {
        super(message);
    }
}
