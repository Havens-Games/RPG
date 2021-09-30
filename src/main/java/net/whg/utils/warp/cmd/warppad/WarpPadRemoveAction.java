package net.whg.utils.warp.cmd.warppad;

import java.io.IOException;

import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.InternalCommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.cmdformat.UnknownArgumentException;
import net.whg.utils.warp.WarpList;

/**
 * Deletes a warp pad.
 */
public class WarpPadRemoveAction extends Subcommand {
    private final WarpList warpList;

    /**
     * Creates a new WarpPadRemoveAction instance.
     * 
     * @param warpList - The warp list to delete warp pads from.
     */
    public WarpPadRemoveAction(WarpList warpList) {
        this.warpList = warpList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var warpPad = warpList.getWarpPad(args[0]);
        if (warpPad == null)
            throw new UnknownArgumentException("Unknown warp pad: %s", args[0]);

        try {
            warpList.removeWarpPad(warpPad);
            WraithLib.log.sendMessage(sender, "Removed warp pad %s.", warpPad.name());
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
        return "remove";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String requiredPermissionNode(CommandSender sender, String[] args) {
        return "wraithlib.warppad.remove." + args[0];
    }
}
