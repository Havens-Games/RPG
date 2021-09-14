package net.whg.utils.events.location;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * A simple trigger that checks whether or not a given location is within the
 * given bounds and would trigger the corresponding event.
 */
public interface LocationTrigger extends Cloneable, ConfigurationSerializable {

    /**
     * Checks whether or not the provided location is within this trigger's target
     * bounds.
     * 
     * @param location - The location to check against.
     * @return True if the location is within the trigger bounds. False otherwise.
     */
    boolean isInBounds(Location location);

    /**
     * Gets the name of this location trigger.
     * 
     * @return The trigger name.
     */
    String name();

    /**
     * Whether or not this location trigger should be triggered with a teleport
     * event.
     * 
     * @return True if teleporting into the given bounds will trigger this
     *         LocationTrigger. False otherwise.
     */
    boolean triggerOnTeleport();
}
