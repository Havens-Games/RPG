package net.whg.utils.warp.cmd;

import org.bukkit.command.CommandSender;

import net.whg.utils.cmdformat.CommandHandler;

/**
 * A simple command that teleports the player to the warp point labeled "spawn".
 */
public class SpawnCommand extends CommandHandler {
    private final SpawnSubcommand spawnSubcommand;

    /**
     * {@inheritDoc}
     * 
     * Registers the default spawn subcommand and overrides the default function.
     */
    public SpawnCommand() {
        super();

        spawnSubcommand = new SpawnSubcommand();
        actions.add(spawnSubcommand);
    }

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    protected boolean defaultFunction(CommandSender sender) {
        return tryExecuteSubcommand(sender, spawnSubcommand, new String[0]);
    }
}
