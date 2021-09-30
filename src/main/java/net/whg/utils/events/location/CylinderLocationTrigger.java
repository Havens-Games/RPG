package net.whg.utils.events.location;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a cylinder location trigger.
 */
@SerializableAs("CylinderLocationTrigger")
public record CylinderLocationTrigger(String name, Location center, float radius, float height,
        boolean triggerOnTeleport) implements LocationTrigger {
    /**
     * Deserializes a CylinderLocationTrigger instance from a provided argument map.
     * 
     * @param args - The serialized map.
     * @return The new instance.
     */
    public static CylinderLocationTrigger deserialize(Map<String, Object> args) {
        var name = (String) args.get("name");
        var world = Bukkit.getWorld((String) args.get("world"));
        var x = (double) args.get("x");
        var y = (double) args.get("y");
        var z = (double) args.get("z");
        var r = (float) (double) args.get("r");
        var h = (float) (double) args.get("h");
        var triggerOnTeleport = (boolean) args.get("triggerOnTeleport");

        var center = new Location(world, x, y, z);
        return new CylinderLocationTrigger(name, center, r, h, triggerOnTeleport);
    }

    /**
     * Serializes this CylinderLocationTrigger instance into an argument map.
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
        map.put("h", height);
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

        var deltaX = location.getX() - center.getX();
        var deltaY = location.getY() - center.getY();
        var deltaZ = location.getZ() - center.getZ();
        var dist = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        return dist < radius && deltaY >= 0 && deltaY < height;
    }
}
