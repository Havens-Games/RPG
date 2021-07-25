package net.whg.utils.whsculpt;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.whg.utils.exceptions.UnknownArgumentException;

public class ColorShuffleAction extends SculptAction {
    @Override
    public void execute(CommandSender sender, String[] args) throws UnknownArgumentException {
        var world = getWorld(args[0]);
        var x = getInteger(args[1]);
        var y = getInteger(args[2]);
        var z = getInteger(args[3]);
        var width = getInteger(args[4]);
        var length = getInteger(args[5]);
        var tileSize = getInteger(args[6]);
        var blocks = getBlockList(args[7]);

        for (int tileX = 0; tileX < width; tileX++) {
            for (int tileY = 0; tileY < length; tileY++) {
                var tileType = blocks[(int) (Math.random() * blocks.length)];
                for (int a = 0; a < tileSize; a++) {
                    for (int b = 0; b < tileSize; b++) {
                        var block = world.getBlockAt(x + tileX * tileSize + a, y, z + tileY * tileSize + b);
                        block.setType(tileType);
                    }
                }
            }
        }

        var placedBlocks = width * length * tileSize * tileSize;
        sender.sendMessage(ChatColor.GREEN + "Placed " + placedBlocks + " blocks.");
    }

    @Override
    public String getUsage() {
        return "<world> <x> <y> <z> <width> <length> <tileSize> <block1,block2,...>";
    }

    @Override
    public String getName() {
        return "colorShuffle";
    }
}
