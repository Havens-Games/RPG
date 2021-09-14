package net.whg.utils.warp.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPad;

public class WarpPadListAction extends Subcommand {
    private final WarpList warpList;

    public WarpPadListAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var sortedList = warpList.getWarpPads().stream().map(WarpPad::name).toList();
        sortedList.sort((a, b) -> a.compareToIgnoreCase(b));

        var list = sortedList.stream().map(point -> {
            var target = warpList.getWarpPad(point).warpPoint();
            return point + ChatColor.GRAY + " -> " + ChatColor.DARK_AQUA + target;
        }).toArray();

        WraithLib.log.sendMessage(sender, "Warp Pads: %s", (Object) list);
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getName() {
        return "list";
    }
}