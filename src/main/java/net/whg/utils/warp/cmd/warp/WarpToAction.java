package net.whg.utils.warp.cmd.warp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.cmdformat.UnknownArgumentException;
import net.whg.utils.warp.WarpList;

/**
 * Teleports the user to the desired warp point.
 */
public class WarpToAction extends Subcommand {
    private final WarpList warpList;

    /**
     * Creates a new WarpToAction.
     * 
     * @param warpList - The warp list to get existing warp points from.
     */
    public WarpToAction(WarpList warpList) {
        this.warpList = warpList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var warpPoint = warpList.getWarpPoint(args[0]);
        if (warpPoint == null)
            throw new UnknownArgumentException("Unknown warp point: %s", args[0]);

        var player = (Player) sender;
        player.teleport(warpPoint.location());

        WraithLib.log.logInfo("Teleported %s to %s.", sender.getName(), warpPoint.name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsage() {
        return "<name>";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "to";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean requiresNoConsole() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requiredPermissionNode(CommandSender sender, String[] args) {
        return "wraithlib.warp.to." + args[0];
    }
}
