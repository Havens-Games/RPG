package net.whg.utils.warp.cmd.warp;

import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPoint;

/**
 * Displays a list of all existing warp locations to the command sender.
 */
public class WarpListAction extends Subcommand {
    private final WarpList warpList;

    /**
     * Creates a new WarpListAction instance.
     * 
     * @param warpList - The warp list to read available warps from.
     */
    public WarpListAction(WarpList warpList) {
        this.warpList = warpList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var sortedList = warpList.getWarpPoints().stream().map(WarpPoint::name).toList();
        sortedList.sort((a, b) -> a.compareToIgnoreCase(b));
        WraithLib.log.sendMessage(sender, "Warp Points: %s", (Object) sortedList.toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsage() {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "list";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requiredPermissionNode(CommandSender sender, String[] args) {
        return "wraithlib.warp.list";
    }
}