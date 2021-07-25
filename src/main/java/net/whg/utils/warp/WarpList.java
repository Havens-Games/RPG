package net.whg.utils.warp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpList {
    private final Map<String, WarpPoint> warpPoints = new HashMap<>();
    private final Map<String, WarpPad> warpPads = new HashMap<>();
    private final File configFile;
    private final YamlConfiguration config;

    public WarpList(JavaPlugin plugin) {
        configFile = new File(plugin.getDataFolder(), "warps.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        loadList();
    }

    public WarpPoint getWarpPoint(String name) {
        return warpPoints.get(name);
    }

    public void updateWarpPoint(WarpPoint warpPoint) throws IOException {
        warpPoints.put(warpPoint.getName(), warpPoint);
        config.set("WarpPoints." + warpPoint.getName(), warpPoint.getLocation());
        config.save(configFile);
    }

    public void removeWarpPoint(String name) throws IOException {
        warpPoints.remove(name);
        config.set("WarpPoints." + name, null);
        config.save(configFile);
    }

    public WarpPad getWarpPad(String name) {
        return warpPads.get(name);
    }

    public void updateWarpPad(WarpPad warpPad) throws IOException {
        warpPads.put(warpPad.getName(), warpPad);
        config.set(String.format("WarpPads.%s.Location", warpPad.getName()), warpPad.getLocation());
        config.set(String.format("WarpPads.%s.Radius", warpPad.getName()), warpPad.getRadius());
        config.set(String.format("WarpPads.%s.Target", warpPad.getName()), warpPad.getWarpPoint());
        config.save(configFile);
    }

    public void removeWarpPad(String name) throws IOException {
        warpPads.remove(name);
        config.set("WarpPads." + name, null);
        config.save(configFile);
    }

    public WarpPad getWarpPadNear(Location location) {
        for (var warpPad : warpPads.values()) {
            var loc = warpPad.getLocation();
            var radius = warpPad.getRadius() * warpPad.getRadius();

            if (loc.getWorld() != location.getWorld())
                continue;

            if (loc.distanceSquared(location) > radius)
                continue;

            return warpPad;
        }

        return null;
    }

    private void loadList() {
        var savedWarpPoints = config.getConfigurationSection("WarpPoints");
        for (var warpPoint : savedWarpPoints.getKeys(false))
            warpPoints.put(warpPoint, new WarpPoint(warpPoint, savedWarpPoints.getLocation(warpPoint)));

        var savedWarpPads = config.getConfigurationSection("WarpPads");
        for (var warpPad : savedWarpPads.getKeys(false)) {
            var savedWarpPad = savedWarpPads.getConfigurationSection(warpPad);
            var location = savedWarpPads.getLocation("Location");
            var radius = (float) savedWarpPads.getDouble("Radius");
            var target = savedWarpPad.getString("Target");
            warpPads.put(warpPad, new WarpPad(warpPad, location, radius, target));
        }
    }

    public Collection<String> listWarpPoints() {
        return warpPoints.keySet();
    }

    public Collection<String> listWarpPads() {
        return warpPads.keySet();
    }

    public List<WarpPad> getWarpPadsToPoint(String warpPoint) {
        var list = new ArrayList<WarpPad>();
        for (var pad : warpPads.values()) {
            if (pad.getWarpPoint().equals(warpPoint))
                list.add(pad);
        }

        return list;
    }
}
