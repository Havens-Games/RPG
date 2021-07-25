package net.whg.utils.exceptions;

/**
 * Thrown when the command sender uses an unknown or unparsable command argument.
 */
public class UnknownArgumentException extends Exception {
    public UnknownArgumentException(String message) {
        super(message);
    }
}
