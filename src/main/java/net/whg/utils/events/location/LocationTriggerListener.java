package net.whg.utils.events.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * A listener that will call LocationTriggerEvents whenever a player enters the
 * given bounds.
 */
public class LocationTriggerListener implements Listener {
    private final List<LocationTrigger> locationTriggers = new ArrayList<>();
    private final Map<UUID, Location> playerDeathLocations = new HashMap<>();

    /**
     * Registers a new location trigger to begin listening for events.
     * 
     * @param trigger - The trigger to register.
     */
    public void registerLocationTrigger(LocationTrigger trigger) {
        locationTriggers.add(trigger);
    }

    /**
     * Unregisters an exiting location trigger to stop listening for events.
     * 
     * @param trigger - The trigger to unregister.
     */
    public void unregisterLocationTrigger(LocationTrigger trigger) {
        locationTriggers.remove(trigger);
    }

    /**
     * Listens for when the player moves and checks against each registered location
     * trigger to see if the player has just entered any of them. For each location
     * trigger that the player has just entered, a new LocationTriggeredEvent is
     * called.
     * 
     * @param e - The event.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent e) {
        var player = e.getPlayer();
        var oldLocation = e.getFrom();
        var newLocation = e.getTo();

        for (var trigger : locationTriggers) {
            if (trigger.isInBounds(newLocation) && !trigger.isInBounds(oldLocation)) {
                Bukkit.getPluginManager().callEvent(new LocationTriggeredEvent(player, trigger));
            }
        }
    }

    /**
     * Listens for each time a player teleports from one location to another. If the
     * player teleports inside of a location trigger from an outside position, and
     * that trigger responds to teleport events, then a new LocationTriggeredEvent
     * is called.
     * 
     * @param e - The event.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        var player = e.getPlayer();
        var oldLocation = e.getFrom();
        var newLocation = e.getTo();

        for (var trigger : locationTriggers) {
            if (!trigger.triggerOnTeleport())
                continue;

            if (trigger.isInBounds(newLocation) && !trigger.isInBounds(oldLocation)) {
                Bukkit.getPluginManager().callEvent(new LocationTriggeredEvent(player, trigger));
            }
        }
    }

    /**
     * Listens for when a player joins the server. If a player joins the server and
     * spawns inside of a location trigger, and that trigger responds to teleport
     * events, a new LocationTriggeredEvent is called.
     * 
     * @param e - The event.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();
        var location = player.getLocation();

        for (var trigger : locationTriggers) {
            if (!trigger.triggerOnTeleport())
                continue;

            if (trigger.isInBounds(location)) {
                Bukkit.getPluginManager().callEvent(new LocationTriggeredEvent(player, trigger));
            }
        }
    }

    /**
     * Listens for when a player respawns and checks if that respawn location is
     * inside of a location trigger (while dying outside of it). If it is, and that
     * location trigger is activated on teleport events, a new
     * LocationTriggeredEvent is called.
     * 
     * @param e - The event.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        var player = e.getPlayer();
        var oldLocation = playerDeathLocations.get(player.getUniqueId());
        var newLocation = e.getRespawnLocation();
        playerDeathLocations.remove(player.getUniqueId());

        // If we can't remember the death location, for some reason?
        if (oldLocation == null)
            return;

        for (var trigger : locationTriggers) {
            if (!trigger.triggerOnTeleport())
                continue;

            if (trigger.isInBounds(newLocation) && !trigger.isInBounds(oldLocation)) {
                Bukkit.getPluginManager().callEvent(new LocationTriggeredEvent(player, trigger));
            }
        }
    }

    /**
     * Listens for whenever a player dies and records their death location.
     * 
     * @param e - The event.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent e) {
        var player = e.getEntity();
        var location = player.getLocation();
        playerDeathLocations.put(player.getUniqueId(), location);
    }

    /**
     * Listens for when a player quits the server, and clears their death location
     * from memory, to avoid possible memory leaks.
     * 
     * @param e - The event.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        var player = e.getPlayer();
        playerDeathLocations.remove(player.getUniqueId());
    }
}
