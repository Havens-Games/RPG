package net.whg.utils.warp;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;

public class WarpListAction extends Subcommand {
    private final WarpList warpList;

    public WarpListAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var sortedList = new ArrayList<String>();
        sortedList.addAll(warpList.listWarpPoints());
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