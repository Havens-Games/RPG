package net.whg.utils.warp.cmd;

import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPoint;

public class WarpListAction extends Subcommand {
    private final WarpList warpList;

    public WarpListAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var sortedList = warpList.getWarpPoints().stream().map(WarpPoint::name).toList();
        sortedList.sort((a, b) -> a.compareToIgnoreCase(b));
        WraithLib.log.sendMessage(sender, "Warp Points: %s", (Object) sortedList.toArray());
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