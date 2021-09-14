package net.whg.utils.events.location;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a spherical location trigger.
 */
@SerializableAs("SphereLocationTrigger")
public record SphereLocationTrigger(String name, Location center, float radius, boolean triggerOnTeleport)
        implements LocationTrigger {
    /**
     * Deserializes a SphereLocationTrigger instance from a provided argument map.
     * 
     * @param args - The serialized map.
     * @return The new instance.
     */
    public static SphereLocationTrigger deserialize(Map<String, Object> args) {
        var name = (String) args.get("name");
        var world = Bukkit.getWorld((String) args.get("world"));
        var x = (double) args.get("x");
        var y = (double) args.get("y");
        var z = (double) args.get("z");
        var r = (float) args.get("r");
        var triggerOnTeleport = (boolean) args.get("triggerOnTeleport");

        var center = new Location(world, x, y, z);
        return new SphereLocationTrigger(name, center, r, triggerOnTeleport);
    }

    /**
     * Serializes this SphereLocationTrigger instance into an argument map.
     * 
     * @return The serialized version of this object.
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        var map = new LinkedHashMap<String, Object>();
        map.put("name", name);
        map.put("world", center.getWorld().getName());
        map.put("x", center.getX());
        map.put("y", center.getY());
        map.put("z", center.getZ());
        map.put("r", radius);
        map.put("triggerOnTeleport", triggerOnTeleport);
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInBounds(Location location) {
        if (location.getWorld() != center.getWorld())
            return false;

        return center.distance(location) < radius;
    }
}
