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
            var variable = args[i].toString();

            if (isNumber(variable))
                formatted[i] = ChatColor.AQUA.toString();
            else
                formatted[i] = ChatColor.DARK_AQUA.toString();

            formatted[i] += variable + ChatColor.GRAY.toString();
        }

        message = String.format(message, formatted);
        sender.sendMessage(ChatColor.GRAY + message);
    }

    public void sendError(String message, Object... args) {
        var formatted = new Object[args.length];

        for (var i = 0; i < formatted.length; i++) {
            var variable = args[i].toString();
            formatted[i] = ChatColor.GOLD.toString();
            formatted[i] += variable + ChatColor.RED.toString();
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
