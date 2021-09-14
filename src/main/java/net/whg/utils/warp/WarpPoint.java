package net.whg.utils.warp;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

@SerializableAs("WarpPoint")
public record WarpPoint(String name, Location location) implements Cloneable, ConfigurationSerializable {
    /**
     * Deserializes a WarpPoint instance from a provided argument map.
     * 
     * @param args - The serialized map.
     * @return The new instance.
     */
    public static WarpPoint deserialize(Map<String, Object> args) {
        var name = (String) args.get("name");
        var location = (Location) args.get("location");
        return new WarpPoint(name, location);
    }

    /**
     * Serializes this WarpPoint instance into an argument map.
     * 
     * @return The serialized version of this object.
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        var map = new LinkedHashMap<String, Object>();
        map.put("name", name);
        map.put("location", location);
        return map;
    }
}