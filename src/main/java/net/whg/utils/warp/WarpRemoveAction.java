package net.whg.utils.warp;

import java.io.IOException;

import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;
import net.whg.utils.exceptions.NoPermissionsException;
import net.whg.utils.exceptions.UnknownArgumentException;
import net.whg.utils.player.CmdPlayer;

public class WarpRemoveAction extends Subcommand {
    private final WarpList warpList;

    public WarpRemoveAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
        if (!sender.isOp())
            throw new NoPermissionsException("You do not have permission to remove warp points!");

        var warpPoint = warpList.getWarpPoint(args[0]);
        if (warpPoint == null)
            throw new UnknownArgumentException("Unknown warp point: '%s'", args[0]);

        var warpPads = warpList.getWarpPadsToPoint(warpPoint.getName());
        var warpPadNames = warpPads.stream().map(a -> a.getName()).toArray();

        try {
            warpList.removeWarpPoint(warpPoint.getName());

            for (var pad : warpPads)
                warpList.removeWarpPad(pad.getName());

            sender.sendConfirmation("Removed warp point '%s' and corresponding warp pads: %s.", warpPoint.getName(),
                    warpPadNames);
        } catch (IOException e) {
            sender.sendError("Failed to save warp list! See console for more information.");
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
}
