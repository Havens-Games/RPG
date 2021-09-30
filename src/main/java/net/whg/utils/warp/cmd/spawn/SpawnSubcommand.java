package net.whg.utils.warp.cmd.spawn;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.cmdformat.UnknownArgumentException;
import net.whg.utils.warp.SpawnPoints;

/**
 * Teleports the command sender to the world's spawn location.
 */
public class SpawnSubcommand extends Subcommand {
    private final SpawnPoints spawnPoints;

    /**
     * Creates a new SpawnSubcommand instance.
     * 
     * @param spawnPoints - The handler for retrieving spawn locations.
     */
    public SpawnSubcommand(SpawnPoints spawnPoints) {
        this.spawnPoints = spawnPoints;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var player = (Player) sender;

        Location spawn;
        if (args.length == 0) {
            spawn = spawnPoints.getGlobalSpawn();
        } else {
            spawn = spawnPoints.getSpawnPoint(args[0]);
            if (spawn == null)
                throw new UnknownArgumentException("Could not find world %s!", args[0]);
        }

        player.teleport(spawn);
        WraithLib.log.sendMessage(player, "Teleporting to spawn...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsage() {
        return "[world]";
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
    public boolean requiresNoConsole() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requiredPermissionNode(CommandSender sender, String[] args) {
        if (args.length == 0)
            return "wraithlib.spawn.global";
        else
            return "wraithlib.spawn." + args[0];
    }
}
