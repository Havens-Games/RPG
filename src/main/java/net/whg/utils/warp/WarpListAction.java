package net.whg.utils.warp;

import java.util.ArrayList;

import net.whg.utils.CmdPlayer;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;

public class WarpListAction extends Subcommand {
    private final WarpList warpList;

    public WarpListAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
        var sortedList = new ArrayList<String>();
        sortedList.addAll(warpList.listWarpPoints());
        sortedList.sort((a, b) -> a.compareToIgnoreCase(b));
        sender.sendConfirmation("Warp Points: %s", (Object) sortedList.toArray());
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