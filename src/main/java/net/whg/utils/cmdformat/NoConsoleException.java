package net.whg.utils.cmdformat;

/**
 * Thrown when a command is executed from the console that should only be
 * executed from a player.
 */
public class NoConsoleException extends CommandException {
    public NoConsoleException() {
        this("This command must be executed as a player!");
    }

    public NoConsoleException(String message) {
        super(message);
    }
}
