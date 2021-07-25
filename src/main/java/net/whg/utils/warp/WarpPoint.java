package net.whg.utils.warp;

import org.bukkit.Location;

public class WarpPoint {
    private final String name;
    private final Location location;

    public WarpPoint(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
