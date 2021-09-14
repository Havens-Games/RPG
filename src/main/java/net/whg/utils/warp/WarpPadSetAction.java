package net.whg.utils.warp;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.cmdformat.UnknownArgumentException;

public class WarpPadSetAction extends Subcommand {
    private final WarpList warpList;

    public WarpPadSetAction(WarpList warpList) {
        this.warpList = warpList;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var player = (Player) sender;
        var name = args[0];
        var location = player.getLocation();
        var radius = getFloat(args[1]);
        var warpPoint = getWarpPoint(args[2]);

        var warpPad = new WarpPad(name, location, radius, warpPoint.name());

        try {
            warpList.updateWarpPad(warpPad);
            WraithLib.log.sendMessage(sender, "Saved warp pad '%s' to '%s'.", name, warpPoint.name());
        } catch (IOException e) {
            WraithLib.log.sendError(sender, "Failed to save warp pad list! See console for more information.");
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

    @Override
    public boolean requiresOp() {
        return true;
    }

    @Override
    public boolean requiresNoConsole() {
        return true;
    }

    protected WarpPoint getWarpPoint(String arg) throws CommandException {
        var warpPoint = warpList.getWarpPoint(arg);
        if (warpPoint == null)
            throw new UnknownArgumentException("Cannot find warp point: %s", arg);

        return warpPoint;
    }
}
