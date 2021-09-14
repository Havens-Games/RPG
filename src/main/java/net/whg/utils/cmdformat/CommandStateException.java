package net.whg.utils.cmdformat;

/**
 * Throw when the command sender tries to execute a command in an illegal state.
 */
public class CommandStateException extends CommandException {
    public CommandStateException(String message) {
        super(message);
    }
}
