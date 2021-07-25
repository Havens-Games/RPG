package net.whg.utils.cmdformat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import net.whg.utils.CmdPlayer;
import net.whg.utils.exceptions.CommandException;

public abstract class CommandHandler implements CommandExecutor {
    protected final List<Subcommand> actions = new ArrayList<>();

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

    private Subcommand getTargetAction(CmdPlayer sender, String name) {
        for (var action : actions) {
            if (action.getName().equalsIgnoreCase(name))
                return action;
        }

        sender.sendError("Unknown subcommand: '%s'", name);
        return null;
    }

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

    public abstract String getName();
}
