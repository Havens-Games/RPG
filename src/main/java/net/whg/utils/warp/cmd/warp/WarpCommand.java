package net.whg.utils.warp.cmd.warp;

import net.whg.utils.cmdformat.CommandHandler;
import net.whg.utils.warp.WarpList;

/**
 * The command namespace for all warp management commands.
 */
public class WarpCommand extends CommandHandler {
    /**
     * Creates a new WarpCommand instance.
     * 
     * @param warpList - The warp list for this namespace instance to manage.
     */
    public WarpCommand(WarpList warpList) {
        actions.add(new WarpToAction(warpList));
        actions.add(new WarpSetAction(warpList));
        actions.add(new WarpListAction(warpList));
        actions.add(new WarpRemoveAction(warpList));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "warp";
    }
}
