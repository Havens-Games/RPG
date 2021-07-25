package net.whg.utils.warp;

import net.whg.utils.cmdformat.CommandHandler;

public class WarpPadCommand extends CommandHandler {

    public WarpPadCommand(WarpList warpList) {
        actions.add(new WarpPadSetAction(warpList));
        actions.add(new WarpPadListAction(warpList));
        actions.add(new WarpPadRemoveAction(warpList));
    }

    @Override
    public String getName() {
        return "warppad";
    }
}
