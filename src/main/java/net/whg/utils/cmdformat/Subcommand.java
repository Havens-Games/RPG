package net.whg.utils.cmdformat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import net.whg.utils.exceptions.CommandException;
import net.whg.utils.exceptions.NoPermissionsException;
import net.whg.utils.exceptions.UnknownArgumentException;
import net.whg.utils.player.CmdPlayer;

/**
 * A utility handler for managing subcommands. Adds a set of useful protected
 * functions for parsing command arguments.
 */
public abstract class Subcommand {
    /**
     * Parses the given argument as a world.
     * 
     * @param arg - The argument to parse.
     * @return The world with the given name.
     * @throws CommandException If there is no world with the given name.
     */
    protected World getWorld(String arg) throws CommandException {
        var world = Bukkit.getWorld(arg);
        if (world == null)
            throw new UnknownArgumentException("Cannot find world: %s", arg);

        return world;
    }

    /**
     * Parses the given argument as an integer.
     * 
     * @param arg - The argument to parse.
     * @return The integer this argument represents.
     * @throws CommandException If the argument could not be parsed as an integer.
     */
    protected int getInteger(String arg) throws CommandException {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new UnknownArgumentException("Cannot parse integer: %s", arg);
        }
    }

    /**
     * Parses the given argument as a float.
     * 
     * @param arg - The argument to parse.
     * @return The float this argument represents.
     * @throws CommandException If the argument could not be parsed as a float.
     */
    protected float getFloat(String arg) throws CommandException {
        try {
            return Float.parseFloat(arg);
        } catch (NumberFormatException e) {
            throw new UnknownArgumentException("Cannot parse float: %s", arg);
        }
    }

    /**
     * Parses the given argument as a list of block types.
     * 
     * @param arg - The argument to parse.
     * @return A list of block types.
     * @throws CommandException If the argument could not be parsed as a list of
     *                          block types, or any of the provided block types
     *                          could not be found.
     */
    protected Material[] getBlockList(String arg) throws CommandException {
        var blockNames = arg.split(",");
        if (blockNames.length == 0)
            throw new NoPermissionsException("Empty block list!");

        var blocks = new Material[blockNames.length];
        for (var i = 0; i < blockNames.length; i++) {
            blocks[i] = Material.matchMaterial(blockNames[i]);

            if (blocks[i] == null)
                throw new UnknownArgumentException("Unknown block type: %s", blockNames[i]);
        }

        return blocks;
    }

    /**
     * Parses the given argument as a block type.
     * 
     * @param arg - The argument to parse.
     * @return The matching block type.
     * @throws CommandException If there is no existing block type that matches the
     *                          given argument.
     */
    protected Material getBlock(String arg) throws CommandException {
        var block = Material.matchMaterial(arg);

        if (block == null)
            throw new UnknownArgumentException("Unknown block type: %s", arg);

        return block;
    }

    /**
     * Executes this subcommand.
     * 
     * @param sender - The command sender.
     * @param args   - The arguments provided to this subcommand.
     * @throws CommandException If an exception is thrown while executing this
     *                          command.
     */
    public abstract void execute(CmdPlayer sender, String[] args) throws CommandException;

    /**
     * Gets the usage string for this subcommand. <br/>
     * <br/>
     * The usage string is a single line of text, represented as space-separated
     * arguments showing the intended position and data type required for each
     * argument. Required arguments are surrounded by '<' and '>', while optional
     * arguments are surrounded by '[' and ']'. <br/>
     * <br/>
     * Example: ``` <radius> <thickness> <height> [placeAir?] [maxBlocksPerTick] ```
     * 
     * @return The usage string.
     */
    public abstract String getUsage();

    /**
     * Gets the name of this subcommand.
     * 
     * @return The name.
     */
    public abstract String getName();
}
