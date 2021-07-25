package net.whg.utils.warp;

import java.io.IOException;

import net.whg.utils.CmdPlayer;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;
import net.whg.utils.exceptions.NoPermissionsException;
import net.whg.utils.exceptions.UnknownArgumentException;

public class WarpPadRemoveAction extends Subcommand {
    private final WarpList warpList;

    public WarpPadRemoveAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
        if (!sender.isOp())
            throw new NoPermissionsException("You do not have permission to remove warp pads!");

        var warpPad = warpList.getWarpPad(args[0]);
        if (warpPad == null)
            throw new UnknownArgumentException("Unknown warp pad: '%s'", args[0]);

        try {
            warpList.removeWarpPad(warpPad.getName());
            sender.sendConfirmation("Removed warp pad '%s'.", warpPad.getName());
        } catch (IOException e) {
            sender.sendError("Failed to save warp pad list! See console for more information.");
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
