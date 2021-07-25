package net.whg.utils.warp;

import java.io.IOException;

import net.whg.utils.CmdPlayer;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;
import net.whg.utils.exceptions.NoConsoleException;
import net.whg.utils.exceptions.NoPermissionsException;

public class WarpPadSetAction extends Subcommand {
    private final WarpList warpList;

    public WarpPadSetAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
        if (!sender.isPlayer())
            throw new NoConsoleException("You must be a player to use warp pads!");

        if (!sender.isOp())
            throw new NoPermissionsException("You do not have permission to create warp pads!");

        var player = sender.getPlayer();
        var name = args[0];
        var location = player.getLocation();
        var radius = getFloat(args[1]);
        var warpPoint = getWarpPoint(args[2]);

        var warpPad = new WarpPad(name, location, radius, warpPoint.getName());

        try {
            warpList.updateWarpPad(warpPad);
            sender.sendConfirmation("Saved warp pad '%s' to '%s'.", name, warpPoint.getName());
        } catch (IOException e) {
            sender.sendError("Failed to save warp pad list! See console for more information.");
            e.printStackTrace();
        }
    }

    @Override
    public String getUsage() {
        return "<name> <radius> <warp point>";
    }

    @Override
    public String getName() {
        return "set";
    }

    protected WarpPoint getWarpPoint(String arg) throws CommandException {
        var warpPoint = warpList.getWarpPoint(arg);
        if (warpPoint == null)
            throw new NoPermissionsException("Cannot find warp point: " + arg);

        return warpPoint;
    }
}
