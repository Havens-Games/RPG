package net.whg.utils.warp.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;

/**
 * Effectively acts as an alias for "/warp to spawn" for anyone that executes
 * this command.
 */
public class SpawnSubcommand extends Subcommand {
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        Bukkit.dispatchCommand(sender, "warp to spawn");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsage() {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "spawn";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requiredPermissionNode(String[] args) {
        return "wraithlib.spawn";
    }
}
