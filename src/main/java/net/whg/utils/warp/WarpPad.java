package net.whg.utils.warp;

import org.bukkit.Location;

public class WarpPad {
    private final String name;
    private final Location location;
    private final float radius;
    private final String warpPoint;

    public WarpPad(String name, Location location, float radius, String warpPoint) {
        this.name = name;
        this.location = location;
        this.radius = radius;
        this.warpPoint = warpPoint;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public float getRadius() {
        return radius;
    }

    public String getWarpPoint() {
        return warpPoint;
    }
}
