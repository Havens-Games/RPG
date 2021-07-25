package net.whg.utils.cmdformat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import net.whg.utils.CmdPlayer;
import net.whg.utils.exceptions.CommandException;
import net.whg.utils.exceptions.NoPermissionsException;

public abstract class Subcommand {
    protected World getWorld(String arg) throws CommandException {
        var world = Bukkit.getWorld(arg);
        if (world == null)
            throw new NoPermissionsException("Cannot find world: " + arg);

        return world;
    }

    protected int getInteger(String arg) throws CommandException {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new NoPermissionsException("Cannot parse integer: " + arg);
        }
    }

    protected float getFloat(String arg) throws CommandException {
        try {
            return Float.parseFloat(arg);
        } catch (NumberFormatException e) {
            throw new NoPermissionsException("Cannot parse float: " + arg);
        }
    }

    protected Material[] getBlockList(String arg) throws CommandException {
        var blockNames = arg.split(",");
        if (blockNames.length == 0)
            throw new NoPermissionsException("Empty block list!");

        var blocks = new Material[blockNames.length];
        for (var i = 0; i < blockNames.length; i++) {
            blocks[i] = Material.matchMaterial(blockNames[i]);

            if (blocks[i] == null)
                throw new NoPermissionsException("Unknown block type: " + blockNames[i]);
        }

        return blocks;
    }

    protected Material getBlock(String arg) throws CommandException {
        var block = Material.matchMaterial(arg);

        if (block == null)
            throw new NoPermissionsException("Unknown block type: " + arg);

        return block;
    }

    public abstract void execute(CmdPlayer sender, String[] args) throws CommandException;

    public abstract String getUsage();

    public abstract String getName();
}
