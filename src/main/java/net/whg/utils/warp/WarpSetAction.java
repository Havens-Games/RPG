package net.whg.utils.warp;

import java.io.IOException;

import net.whg.utils.CmdPlayer;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;
import net.whg.utils.exceptions.NoConsoleException;
import net.whg.utils.exceptions.NoPermissionsException;

public class WarpSetAction extends Subcommand {
    private final WarpList warpList;

    public WarpSetAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
        if (!sender.isPlayer())
            throw new NoConsoleException("You must be a player to use warp points!");

        if (!sender.isOp())
            throw new NoPermissionsException("You do not have permission to create warp points!");

        var player = sender.getPlayer();
        var warpPoint = new WarpPoint(args[0], player.getLocation());

        try {
            warpList.updateWarpPoint(warpPoint);
            sender.sendConfirmation("Saved warp point '%s'.", warpPoint);
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
        return "set";
    }
}
