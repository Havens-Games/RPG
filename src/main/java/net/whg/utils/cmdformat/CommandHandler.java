package net.whg.utils.cmdformat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.whg.utils.WraithLib;

/**
 * A custom utility handler for managing commands that contain several
 * subcommands.
 */
public abstract class CommandHandler implements CommandExecutor {
    protected final List<Subcommand> actions = new ArrayList<>();
    private final Subcommand helpSubcommand;

    /**
     * Creates a new CommandHandler and initializes the default help subcommand.
     */
    protected CommandHandler() {
        helpSubcommand = new HelpSubcommand(actions);
        actions.add(helpSubcommand);
    }

    /**
     * Gets the subcommand that the command sender is trying to execute. The search
     * ignores casing. If the command sender requested a subcommand that does not
     * exist, an error message is sent to the command sender.
     * 
     * @param sender - The command sender.
     * @param name   - The name of the subcommand to look for.
     * @return The subcommand that was executed, or null if there is no match
     *         subcommand.
     */
    private Subcommand getTargetAction(CommandSender sender, String name) {
        for (var action : actions) {
            if (action.getName().equalsIgnoreCase(name))
                return action;
        }

        WraithLib.log.sendError(sender, "Unknown subcommand: '%s'", name);
        return null;
    }

    /**
     * Compares the number of arguments that the command sender sent against the
     * number of required and optional arguments that are requested by the
     * subcommand. This command only compares the number of arguments, making sure
     * that the number of provided arguments >= the number of required arguments,
     * and <= the total number of possible arguments.
     * 
     * In the usage string, required arguments are assumed to be surrounded by '<'
     * and '>'. All other arguments are assumed to be optional.
     * 
     * @param sender - The command sender.
     * @param usage  - The intended usage string.
     * @param args   - The actual arguments sent by the command sender.
     * @return True if the argument count is valid. False otherwise.
     */
    private boolean verifyArgs(CommandSender sender, String usage, String[] args) {
        var expected = usage.split(" ");

        var min = 0;
        var max = expected.length;

        for (var i = 0; i < expected.length; i++) {
            if (expected[i].matches("^\\<.*\\>$"))
                min++;
        }

        if (args.length < min || args.length > max) {
            tryExecuteSubcommand(sender, helpSubcommand, new String[0]);
            return false;
        }

        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel,
            @NotNull String[] args) {
        if (args.length == 0)
            return defaultFunction(sender);

        var actionName = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        var action = getTargetAction(sender, actionName);
        if (action == null)
            return false;

        return tryExecuteSubcommand(sender, action, args);
    }

    /**
     * Executes a given subcommand, catching exceptions, logging errors, and
     * verifying command arguments and subcommand state as needed.
     * 
     * @param sender - The command sender.
     * @param action - The subcommand being executed.
     * @param args   - The command arguments.
     * @return True if the command was executed successfully. False otherwise.
     */
    protected boolean tryExecuteSubcommand(CommandSender sender, Subcommand action, String[] args) {
        if (!verifyArgs(sender, action.getUsage(), args))
            return false;

        try {
            checkSubcommandState(sender, action);
            action.execute(sender, args);
            return true;
        } catch (InternalCommandException e) {
            e.printToPlayer(sender);
            e.printStackTrace();
            return false;
        } catch (CommandException e) {
            e.printToPlayer(sender);
            return false;
        } catch (Exception e) {
            var internal = new InternalCommandException(e);
            internal.printToPlayer(sender);
            internal.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if the command sender meets the requirements provided by the
     * subcommand to be executed. Throws a corresponding command exception if the
     * requirements are not met.
     * 
     * @param sender     - The command sender.
     * @param subcommand - The subcommand to check against.
     * @throws CommandException If the command sender fails to meet one of the
     *                          requirements provided by the subcommand.
     */
    private void checkSubcommandState(CommandSender sender, Subcommand subcommand) throws CommandException {
        if (subcommand.requiresOp() && !sender.isOp())
            throw new NoPermissionsException();

        if (subcommand.requiresNoConsole() && !(sender instanceof Player))
            throw new NoConsoleException();
    }

    /**
     * The function that is executed if the command sender executes this command
     * with no provided subcommand. By default, this will supply the help menu for
     * this command set.
     * 
     * @param sender - The command sender.
     */
    protected boolean defaultFunction(CommandSender sender) {
        return tryExecuteSubcommand(sender, helpSubcommand, new String[0]);
    }

    /**
     * Gets the name of this command.
     * 
     * @return The command name.
     */
    public abstract String getName();
}
