package net.whg.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CmdPlayer {
    private final CommandSender sender;

    public CmdPlayer(CommandSender sender) {
        this.sender = sender;
    }

    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    public void sendMessage(String[] message) {
        sender.sendMessage(message);
    }

    public void sendConfirmation(String message, Object... args) {
        var formatted = new Object[args.length];

        for (var i = 0; i < formatted.length; i++) {
            if (args[i]instanceof Object[] arr) {
                formatted[i] = formatArray(arr, ChatColor.GRAY, ChatColor.DARK_AQUA) + ChatColor.GRAY;
                continue;
            }

            var variable = args[i].toString();
            if (isNumber(variable))
                formatted[i] = ChatColor.AQUA + variable + ChatColor.GRAY;
            else
                formatted[i] = ChatColor.DARK_AQUA + variable + ChatColor.GRAY;
        }

        message = String.format(message, formatted);
        sender.sendMessage(ChatColor.GRAY + message);
    }

    private String formatArray(Object[] array, ChatColor seperatorColor, ChatColor objectColor) {
        var str = objectColor.toString();

        var first = true;
        for (var elem : array) {
            if (!first)
                str += seperatorColor + ", " + objectColor;

            str += elem;
            first = false;
        }

        return str;
    }

    public void sendError(String message, Object... args) {
        var formatted = new Object[args.length];

        for (var i = 0; i < formatted.length; i++) {
            if (args[i]instanceof Object[] arr)
                formatted[i] = formatArray(arr, ChatColor.RED, ChatColor.GOLD) + ChatColor.RED;
            else
                formatted[i] = ChatColor.GOLD + args[i].toString() + ChatColor.RED;
        }

        message = String.format(message, formatted);
        sender.sendMessage(ChatColor.RED + message);
    }

    private boolean isNumber(String arg) {
        try {
            Float.parseFloat(arg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public boolean isOp() {
        return sender.isOp();
    }

    public Player getPlayer() {
        return (Player) sender;
    }
}
