package net.whg.utils.exceptions;

/**
 * Thrown when the command sender does not have permission to execute the given
 * command.
 */
public class NoPermissionsException extends CommandException {
    public NoPermissionsException(String message) {
        super(message);
    }
}
