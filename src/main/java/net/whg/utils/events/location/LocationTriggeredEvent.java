package net.whg.utils.events.location;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * An event that is called when a player enters a location trigger.
 */
public class LocationTriggeredEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    /**
     * Gets the HandlerList attached to this event type.
     * 
     * @return The handler list.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Player player;
    private final LocationTrigger locationTrigger;

    LocationTriggeredEvent(Player player, LocationTrigger locationTrigger) {
        this.player = player;
        this.locationTrigger = locationTrigger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return getHandlerList();
    }

    /**
     * Gets the player involved in this event.
     * 
     * @return The player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the LocationTrigger that triggered this event.
     * 
     * @return The location trigger.
     */
    public LocationTrigger getLocationTrigger() {
        return locationTrigger;
    }
}
