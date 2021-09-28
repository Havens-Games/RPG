package net.whg.utils.warp.cmd.warppad;

import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.whg.utils.WraithLib;
import net.whg.utils.cmdformat.CommandException;
import net.whg.utils.cmdformat.InternalCommandException;
import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.cmdformat.UnknownArgumentException;
import net.whg.utils.events.location.CylinderLocationTrigger;
import net.whg.utils.events.location.LocationTrigger;
import net.whg.utils.events.location.SphereLocationTrigger;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPad;
import net.whg.utils.warp.WarpPoint;

/**
 * Creates a new warp pad.
 */
public class WarpPadSetAction extends Subcommand {
    private final WarpList warpList;

    /**
     * Creates a new WarpPadSetAction instance.
     * 
     * @param warpList - The warp list to add the warp pad to.
     */
    public WarpPadSetAction(WarpList warpList) {
        this.warpList = warpList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) throws CommandException {
        var player = (Player) sender;
        var name = args[0];
        var location = player.getLocation();
        var radius = getFloat(args[1]);
        var warpPoint = getWarpPoint(args[2]);
        LocationTrigger locationTrigger = new SphereLocationTrigger(name, location, radius, true);

        if (args.length > 3 && getBoolean(args[3])) {
            var cylHeight = 1;

            if (args.length > 4)
                cylHeight = getInteger(args[4]);

            locationTrigger = new CylinderLocationTrigger(name, location, radius, cylHeight, true);
        }

        try {
            var oldWarpPad = warpList.getWarpPad(args[0]);
            if (oldWarpPad != null)
                warpList.removeWarpPad(oldWarpPad);

            warpList.addWarpPad(new WarpPad(locationTrigger, warpPoint.name()));
            WraithLib.log.sendMessage(sender, "Saved warp pad '%s' to '%s'.", name, warpPoint.name());
        } catch (IOException e) {
            throw new InternalCommandException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUsage() {
        return "<name> <radius> <warp point> [cylinderMode] [cylinderHeight]";
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
        return "wraithlib.warppad.set." + args[0];
    }

    /**
     * Tries to parse the provided command argument as a warp point.
     * 
     * @param arg - The command argument to parse.
     * @return The warp point with the existing name.
     * @throws CommandException If the warp point could not be found.
     */
    protected WarpPoint getWarpPoint(String arg) throws CommandException {
        var warpPoint = warpList.getWarpPoint(arg);
        if (warpPoint == null)
            throw new UnknownArgumentException("Cannot find warp point: %s", arg);

        return warpPoint;
    }
}
