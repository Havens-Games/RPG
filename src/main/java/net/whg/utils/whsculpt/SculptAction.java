package net.whg.utils.whsculpt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import net.whg.utils.exceptions.UnknownArgumentException;

public abstract class SculptAction {
    protected World getWorld(String arg) throws UnknownArgumentException {
        var world = Bukkit.getWorld(arg);
        if (world == null)
            throw new UnknownArgumentException("Cannot find world: " + arg);

        return world;
    }

    protected int getInteger(String arg) throws UnknownArgumentException {
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new UnknownArgumentException("Cannot parse integer: " + arg);
        }
    }

    protected Material[] getBlockList(String arg) throws UnknownArgumentException {
        var blockNames = arg.split(",");
        if (blockNames.length == 0)
            throw new UnknownArgumentException("Empty block list!");

        var blocks = new Material[blockNames.length];
        for (var i = 0; i < blockNames.length; i++) {
            blocks[i] = Material.matchMaterial(blockNames[i]);

            if (blocks[i] == null)
                throw new UnknownArgumentException("Unknown block type: " + blockNames[i]);
        }

        return blocks;
    }

    protected Material getBlock(String arg) throws UnknownArgumentException {
        var block = Material.matchMaterial(arg);

        if (block == null)
            throw new UnknownArgumentException("Unknown block type: " + arg);

        return block;
    }

    public abstract void execute(CommandSender sender, String[] args) throws UnknownArgumentException;

    public abstract String getUsage();

    public abstract String getName();
}
