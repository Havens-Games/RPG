package net.whg.utils.warp.cmd.warppad;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPad;

/**
 * Lists all existing warp pads to the command sender.
 */
public class WarpPadListAction extends Subcommand {
    private final WarpList warpList;

    /**
     * Creates a new WarpPadListAction instance.
     * 
     * @param warpList - The warp list to read existing warp pads from.
     */
    public WarpPadListAction(WarpList warpList) {
        this.warpList = warpList;
    }

    /**
     * {@inheritDoc}
     */
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
        return "wraithlib.warppad.list";
    }
}