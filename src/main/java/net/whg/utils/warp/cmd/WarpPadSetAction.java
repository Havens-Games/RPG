package net.whg.utils.warp.cmd;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.cmdformat.UnknownArgumentException;
import net.whg.utils.events.location.SphereLocationTrigger;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPad;
import net.whg.utils.warp.WarpPoint;

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

        var locationTrigger = new SphereLocationTrigger(name, location, radius, true);
        var warpPad = new WarpPad(locationTrigger, warpPoint.name());

        try {
            var oldWarpPad = warpList.getWarpPad(args[0]);
            if (oldWarpPad != null) {
                warpList.removeWarpPad(oldWarpPad);
            }

            warpList.addWarpPad(warpPad);
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
