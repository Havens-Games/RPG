package net.whg.utils.warp.cmd;

import net.whg.utils.cmdformat.CommandHandler;
import net.whg.utils.warp.WarpList;

/**
 * The command namespace for all warp pad management command.
 */
public class WarpPadCommand extends CommandHandler {
    /**
     * Creates a new WarpPadCommand instance.
     * 
     * @param warpList - The warp list this command namespace instance managed.
     */
    public WarpPadCommand(WarpList warpList) {
        actions.add(new WarpPadSetAction(warpList));
        actions.add(new WarpPadListAction(warpList));
        actions.add(new WarpPadRemoveAction(warpList));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "warppad";
    }
}
