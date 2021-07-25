package net.whg.utils.warp;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import net.whg.utils.CmdPlayer;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;

public class WarpPadListAction extends Subcommand {
    private final WarpList warpList;

    public WarpPadListAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
        var sortedList = new ArrayList<String>();
        sortedList.addAll(warpList.listWarpPads());
        sortedList.sort((a, b) -> a.compareToIgnoreCase(b));

        var list = sortedList.stream().map(point -> {
            var target = warpList.getWarpPad(point).getWarpPoint();
            return point + ChatColor.GRAY + " -> " + ChatColor.DARK_AQUA + target;
        }).toArray();

        sender.sendConfirmation("Warp Pads: %s", (Object) list);
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