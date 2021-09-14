package net.whg.utils.warp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.whg.utils.events.location.LocationTriggeredEvent;

/**
 * Listens for location triggered events and triggers any corresponding warp
 * pads.
 */
public class WarpListener implements Listener {
    private final WarpList warpList;

    /**
     * Creates a new warp listener and triggers and links to any warp pads and warp
     * points within the provided warp list.
     * 
     * @param warpList - The warp list.
     */
    public WarpListener(WarpList warpList) {
        this.warpList = warpList;
    }

    /**
     * Listens for any LocationTriggeredEvents and checks to see if there are any
     * warp pads that were attached to those triggers. If a match if found, the
     * player that triggered this event is teleported to the corresponding warp
     * point. If multiple warp pads are triggered at once, only the first warp pad
     * within the warp list is used.
     * 
     * @param e - The event.
     */
    @EventHandler
    public void onLocationTriggeredEvent(LocationTriggeredEvent e) {
        for (var warpPad : warpList.getWarpPads()) {
            if (warpPad.locationTrigger() != e.getLocationTrigger())
                continue;

            var warpPoint = warpList.getWarpPoint(warpPad.warpPoint());
            e.getPlayer().teleport(warpPoint.location());
            return;
        }
    }
}
