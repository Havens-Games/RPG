package net.whg.utils.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import net.whg.utils.exceptions.UnknownArgumentException;
import net.whg.utils.whsculpt.SculptAction;

public class RemoveKeepOneAction extends SculptAction {
    @Override
    public void execute(CommandSender sender, String[] args) throws UnknownArgumentException {
        var world = getWorld(args[0]);
        var x1 = getInteger(args[1]);
        var y1 = getInteger(args[2]);
        var z1 = getInteger(args[3]);

        var x2 = getInteger(args[4]);
        var y2 = getInteger(args[5]);
        var z2 = getInteger(args[6]);

        var toKeep = getBlock(args[7]);
        var removedBlocks = 0;
        var keptBlocks = 0;

        for (var a = x1; a <= x2; a++) {
            for (var b = y1; b <= y2; b++) {
                for (var c = z1; c <= z2; c++) {
                    var block = world.getBlockAt(a, b, c);
                    if (block.getType() == Material.AIR)
                        continue;

                    if (block.getType() != toKeep) {
                        block.setType(Material.AIR);
                        removedBlocks++;
                    } else
                        keptBlocks++;
                }
            }
        }

        sender.sendMessage(ChatColor.GREEN + "Removed " + removedBlocks + " blocks. (Kept " + keptBlocks + ")");
    }

    @Override
    public String getUsage() {
        return "<world> <x1> <y1> <z1> <x2> <y2> <z2> <blockToKeep>";
    }

    @Override
    public String getName() {
        return "removeKeepOne";
    }
}
