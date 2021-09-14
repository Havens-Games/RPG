package net.whg.utils.warp.cmd;

import java.io.IOException;

import org.bukkit.command.CommandSender;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.cmdformat.UnknownArgumentException;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPad;

public class WarpRemoveAction extends Subcommand {
    private final WarpList warpList;

    public WarpRemoveAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var warpPoint = warpList.getWarpPoint(args[0]);
        if (warpPoint == null)
            throw new UnknownArgumentException("Unknown warp point: %s", args[0]);

        try {
            var removedWarpPads = warpList.removeWarpPoint(warpPoint);
            var warpPadNames = removedWarpPads.stream().map(WarpPad::name).toArray();

            WraithLib.log.sendMessage(sender, "Removed warp point '%s' and corresponding warp pads: %s.",
                    warpPoint.name(), warpPadNames);
        } catch (IOException e) {
            WraithLib.log.sendError(sender, "Failed to save warp list! See console for more information.");
            e.printStackTrace();
        }
    }

    @Override
    public String getUsage() {
        return "<name>";
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public boolean requiresOp() {
        return true;
    }
}
