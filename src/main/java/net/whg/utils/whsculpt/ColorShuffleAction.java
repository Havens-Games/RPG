package net.whg.utils.whsculpt;

import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;
import net.whg.utils.player.CmdPlayer;

public class ColorShuffleAction extends Subcommand {
    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
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
        sender.sendConfirmation("Placed %s blocks.", placedBlocks);
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
