package net.whg.utils.cmdformat;

/**
 * Thrown if there is an internal error that occurs while the command is being
 * executed.
 */
public class InternalCommandException extends CommandException {
    private final Exception exception;

    public InternalCommandException(Exception e) {
        super("An internal error has occured while preforming this command. Please report this error to a server admin for further information.");
        this.exception = e;
    }

    @Override
    public void printStackTrace() {
        exception.printStackTrace();
    }
}
