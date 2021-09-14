package net.whg.utils.warp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WarpListener implements Listener {
    private final WarpList warpList;

    public WarpListener(WarpList warpList) {
        this.warpList = warpList;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        var player = e.getPlayer();
        var location = player.getLocation();
        var warpPad = warpList.getWarpPadNear(location);
        if (warpPad == null)
            return;

        var warpPoint = warpList.getWarpPoint(warpPad.warpPoint());
        if (warpPoint == null)
            return; // Uhh, corrupted config file?

        player.teleport(warpPoint.location());
    }
}
