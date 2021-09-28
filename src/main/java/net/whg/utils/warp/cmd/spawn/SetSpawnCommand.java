package net.whg.utils.warp.cmd.spawn;

import net.whg.utils.cmdformat.CommandHandler;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.warp.SpawnPoints;

/**
 * A command that sets the world spawn location to the player's current
 * position.
 */
public class SetSpawnCommand extends CommandHandler {
    private final SetSpawnSubcommand spawnSubcommand;

    /**
     * {@inheritDoc}
     * 
     * Registers the default set spawn subcommand and overrides the default
     * function.
     * 
     * @param spawnPoints - The handler for assigning spawn locations.
     */
    public SetSpawnCommand(SpawnPoints spawnPoints) {
        spawnSubcommand = new SetSpawnSubcommand(spawnPoints);
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
    protected Subcommand defaultSubcommand() {
        return spawnSubcommand;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isRootCommand() {
        return true;
    }
}
