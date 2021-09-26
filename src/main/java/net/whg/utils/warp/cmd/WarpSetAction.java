package net.whg.utils.warp.cmd;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.InternalCommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPoint;

/**
 * Creates a new warp point.
 */
public class WarpSetAction extends Subcommand {
    private final WarpList warpList;

    /**
     * Creates a new WarpSetAction instance.
     * 
     * @param warpList - The warp list to add the warp point to.
     */
    public WarpSetAction(WarpList warpList) {
        this.warpList = warpList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var player = (Player) sender;
        var warpPoint = new WarpPoint(args[0], player.getLocation());

        try {
            var oldWarpPoint = warpList.getWarpPoint(args[0]);
            if (oldWarpPoint != null) {
                warpList.removeWarpPoint(oldWarpPoint);
            }

            warpList.addWarpPoint(warpPoint);
            WraithLib.log.sendMessage(sender, "Saved warp point '%s'.", warpPoint.name());
        } catch (IOException e) {
            throw new InternalCommandException(e);
        }
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
        return "set";
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
    public String requiredPermissionNode(String[] args) {
        return "wraithlib.warp.set." + args[0];
    }
}
