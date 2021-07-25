package net.whg.utils.whsculpt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import net.whg.utils.commands.ColorShuffleAction;
import net.whg.utils.commands.RemoveKeepOneAction;
import net.whg.utils.exceptions.UnknownArgumentException;

public class WHSculptCommand implements CommandExecutor {
    private final List<SculptAction> actions = new ArrayList<>();

    public WHSculptCommand() {
        actions.add(new ColorShuffleAction());
        actions.add(new RemoveKeepOneAction());
    }

    private void listActions(CommandSender sender) {
        var lines = new String[actions.size() + 1];
        lines[0] = "Available Actions:";

        for (int i = 0; i < actions.size(); i++) {
            var action = actions.get(i);
            lines[i + 1] = String.format("%s/whsculpt %s %s", ChatColor.GOLD, action.getName(), action.getUsage());
        }

        sender.sendMessage(lines);
    }

    private SculptAction getTargetAction(CommandSender sender, String name) {
        for (var action : actions) {
            if (action.getName().equalsIgnoreCase(name))
                return action;
        }

        sender.sendMessage(ChatColor.RED + "Unknown subcommand: " + name);
        return null;
    }

    private boolean verifyArgs(CommandSender sender, String usage, String[] args) {
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

        if (args.length == 0) {
            listActions(sender);
            return true;
        }

        var actionName = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        var action = getTargetAction(sender, actionName);
        if (action == null)
            return false;

        if (!verifyArgs(sender, action.getUsage(), args))
            return false;

        try {
            action.execute(sender, args);
            return true;
        } catch (UnknownArgumentException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            return false;
        }
    }

}
