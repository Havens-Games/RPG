package net.whg.utils.warp;

import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;
import net.whg.utils.exceptions.NoConsoleException;
import net.whg.utils.exceptions.UnknownArgumentException;
import net.whg.utils.player.CmdPlayer;

public class WarpToAction extends Subcommand {
    private final WarpList warpList;

    public WarpToAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
        if (!sender.isPlayer())
            throw new NoConsoleException("You must be a player to use warp points!");

        var warpPoint = warpList.getWarpPoint(args[0]);
        if (warpPoint == null)
            throw new UnknownArgumentException("Unknown warp point: '%s'", args[0]);

        var player = sender.getPlayer();
        player.teleport(warpPoint.getLocation());
    }

    @Override
    public String getUsage() {
        return "<name>";
    }

    @Override
    public String getName() {
        return "to";
    }
}
