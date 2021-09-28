package net.whg.utils.warp.cmd.spawn;

import net.whg.utils.cmdformat.CommandHandler;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.warp.SpawnPoints;

/**
 * A simple command that teleports the player to the warp point labeled "spawn".
 */
public class SpawnCommand extends CommandHandler {
    private final SpawnSubcommand spawnSubcommand;

    /**
     * {@inheritDoc}
     * 
     * Registers the default spawn subcommand and overrides the default function.
     * 
     * @param spawnPoints - The handler for retrieving spawn locations.
     */
    public SpawnCommand(SpawnPoints spawnPoints) {
        spawnSubcommand = new SpawnSubcommand(spawnPoints);
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
