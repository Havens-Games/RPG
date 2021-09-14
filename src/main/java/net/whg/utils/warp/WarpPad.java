package net.whg.utils.warp;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import net.whg.utils.events.location.LocationTrigger;

/**
 * Represents a location trigger that will cause a player to be teleported to a
 * target warp point when activated.
 */
@SerializableAs("WarpPad")
public record WarpPad(LocationTrigger locationTrigger, String warpPoint)
        implements Cloneable, ConfigurationSerializable {
    /**
     * Deserializes a WarpPad instance from a provided argument map.
     * 
     * @param args - The serialized map.
     * @return The new instance.
     */
    public static WarpPad deserialize(Map<String, Object> args) {
        var locationTrigger = (LocationTrigger) args.get("trigger");
        var warpPoint = (String) args.get("warpPoint");
        return new WarpPad(locationTrigger, warpPoint);
    }

    /**
     * Serializes this WarpPad instance into an argument map.
     * 
     * @return The serialized version of this object.
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        var map = new LinkedHashMap<String, Object>();
        map.put("trigger", locationTrigger);
        map.put("warpPoint", warpPoint);
        return map;
    }

    /**
     * Gets the name of this WarpPad. This value is taken from the location trigger
     * name.
     * 
     * @return The warp pad name.
     */
    public String name() {
        return locationTrigger.name();
    }
}
