package net.whg.utils.warp.cmd.spawn;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.cmdformat.UnknownArgumentException;
import net.whg.utils.warp.SpawnPoints;

/**
 * Effectively acts as an alias for "/warp to spawn" for anyone that executes
 * this command.
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

        if (args.length == 0) {
            var spawn = spawnPoints.getSpawnPoint(player.getWorld());
            player.teleport(spawn);
        } else {
            var spawn = spawnPoints.getSpawnPoint(args[0]);

            if (spawn == null)
                throw new UnknownArgumentException("Could not find world %s!", args[0]);

            player.teleport(spawn);
        }
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
    public String requiredPermissionNode(String[] args) {
        if (args.length == 0)
            return "wraithlib.spawn";
        else
            return "wraithlib.spawn." + args[0];
    }
}
