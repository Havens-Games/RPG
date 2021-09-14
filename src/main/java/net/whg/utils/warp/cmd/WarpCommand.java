package net.whg.utils.warp.cmd;

import net.whg.utils.cmdformat.CommandHandler;
import net.whg.utils.warp.WarpList;

public class WarpCommand extends CommandHandler {

    public WarpCommand(WarpList warpList) {
        actions.add(new WarpToAction(warpList));
        actions.add(new WarpSetAction(warpList));
        actions.add(new WarpListAction(warpList));
        actions.add(new WarpRemoveAction(warpList));
    }

    @Override
    public String getName() {
        return "warp";
    }
}
