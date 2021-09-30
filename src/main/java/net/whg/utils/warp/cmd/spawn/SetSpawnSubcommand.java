package net.whg.utils.warp.cmd.spawn;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.InternalCommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.warp.SpawnPoints;

/**
 * A command that sets the world spawn location to the player's current
 * position.
 */
public class SetSpawnSubcommand extends Subcommand {
    private final SpawnPoints spawnPoints;

    /**
     * Creates a new SetSpawnSubcommand instance.
     * 
     * @param spawnPoints - The handler for assigning spawn locations.
     */
    public SetSpawnSubcommand(SpawnPoints spawnPoints) {
        this.spawnPoints = spawnPoints;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var player = (Player) sender;
        var worldName = player.getWorld().getName();
        var location = player.getLocation();
        var global = false;

        if (args.length == 1)
            global = Boolean.parseBoolean(args[0]);

        try {
            if (global) {
                spawnPoints.setGlobalSpawn(location);
                WraithLib.log.sendMessage(player, "Server spawn position updated.");
            } else {
                spawnPoints.setSpawnPoint(worldName, location);
                WraithLib.log.sendMessage(player, "World %s spawn position updated.", worldName);
            }
        } catch (IOException e) {
            throw new InternalCommandException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsage() {
        return "[global T/F]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "setspawn";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean requiresNoConsole() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requiredPermissionNode(CommandSender sender, String[] args) {
        if (args.length == 0 && args[0].equalsIgnoreCase("true")) {
            var player = (Player) sender;
            return "wraithlib.setspawn." + player.getWorld().getName();
        } else {
            return "wraithlib.setspawn.global";
        }
    }
}
