package net.whg.utils.cmdformat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import net.whg.utils.exceptions.CommandException;
import net.whg.utils.player.CmdPlayer;

/**
 * A custom utility handler for managing commands that contain several
 * subcommands.
 */
public abstract class CommandHandler implements CommandExecutor {
    protected final List<Subcommand> actions = new ArrayList<>();

    /**
     * Sends a list of all available subcommands to the command sender as a
     * formatted message.
     * 
     * @param sender - The command sender to send the message to.
     */
    private void listActions(CmdPlayer sender) {
        var lines = new String[actions.size() + 1];
        lines[0] = ChatColor.GRAY + "Available Actions:";

        for (int i = 0; i < actions.size(); i++) {
            var action = actions.get(i);
            lines[i + 1] = String.format("%s/%s %s%s %s", ChatColor.DARK_AQUA, getName(), ChatColor.GRAY,
                    action.getName(), action.getUsage());
        }

        sender.sendMessage(lines);
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
    private Subcommand getTargetAction(CmdPlayer sender, String name) {
        for (var action : actions) {
            if (action.getName().equalsIgnoreCase(name))
                return action;
        }

        sender.sendError("Unknown subcommand: '%s'", name);
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
    private boolean verifyArgs(CmdPlayer sender, String usage, String[] args) {
        var expected = usage.split(" ");

        var min = 0;
        var max = expected.length;

        for (var i = 0; i < expected.length; i++) {
            if (expected[i].matches("^\\<.*\\>$"))
                min++;
        }

        if (args.length < min || args.length > max) {
            listActions(sender);
            return false;
        }

        return true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String cmdLabel,
            @NotNull String[] args) {

        var cmdPlayer = new CmdPlayer(sender);
        if (args.length == 0) {
            listActions(cmdPlayer);
            return true;
        }

        var actionName = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        var action = getTargetAction(cmdPlayer, actionName);
        if (action == null)
            return false;

        if (!verifyArgs(cmdPlayer, action.getUsage(), args))
            return false;

        try {
            action.execute(cmdPlayer, args);
            return true;
        } catch (CommandException e) {
            e.printToPlayer(cmdPlayer);
            return false;
        }
    }

    /**
     * Gets the name of this command.
     * 
     * @return The command name.
     */
    public abstract String getName();
}
