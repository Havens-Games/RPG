package net.whg.utils.warp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import net.whg.utils.SafeArrayList;
import net.whg.utils.events.location.LocationTriggerListener;

/**
 * Contains a list of warp points and warp pads that is automatically
 * synchronized with a config file within the plugin data folder.
 */
public class WarpList {
    private static final String WARP_POINTS_CONFIG_PREFIX = "WarpPoints.";
    private static final String WARP_PADS_CONFIG_PREFIX = "WarpPads.";

    private final SafeArrayList<WarpPoint> warpPoints = new SafeArrayList<>();
    private final SafeArrayList<WarpPad> warpPads = new SafeArrayList<>();
    private final LocationTriggerListener locationTriggers;
    private final YamlConfiguration config;
    private final File configFile;

    /**
     * Creates a new WarpList object and loads all existing warp points and warp
     * pads from the config file.
     * 
     * @param locationTriggers - The location triggers handler that this warp list
     *                         should use for managing warp pads.
     */
    public WarpList(LocationTriggerListener locationTriggers) {
        this.locationTriggers = locationTriggers;

        var plugin = Bukkit.getPluginManager().getPlugin("WraithLib");
        configFile = new File(plugin.getDataFolder(), "warps.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        loadList();
    }

    /**
     * Loads all warp pads and warp points from the config file.
     */
    private void loadList() {
        var savedWarpPoints = config.getConfigurationSection("WarpPoints");
        if (savedWarpPoints != null) {
            for (var warpPointName : savedWarpPoints.getKeys(false)) {
                var warpPoint = (WarpPoint) savedWarpPoints.get(warpPointName);
                warpPoints.add(warpPoint);
            }
        }

        var savedWarpPads = config.getConfigurationSection("WarpPads");
        if (savedWarpPads != null) {
            for (var warpPadName : savedWarpPads.getKeys(false)) {
                var warpPad = (WarpPad) savedWarpPads.get(warpPadName);
                locationTriggers.registerLocationTrigger(warpPad.locationTrigger());
                warpPads.add(warpPad);
            }
        }
    }

    /**
     * Gets the warp point in this list with the given name.
     * 
     * @param name - The name of the warp point.
     * @return The warp point with the matching name, or null if there is no warp
     *         point with the given name.
     */
    public WarpPoint getWarpPoint(String name) {
        for (var warpPoint : warpPoints) {
            if (warpPoint.name().equals(name))
                return warpPoint;
        }

        return null;
    }

    /**
     * Adds a new warp point to this list and updates the config file.
     * 
     * @param warpPoint - The warp point to add.
     * @throws IllegalArgumentException If there is already a warp point with a
     *                                  matching name.
     * @throws IOException              If there is an IO issue while updating the
     *                                  config file.
     */
    public void addWarpPoint(WarpPoint warpPoint) throws IOException {
        if (getWarpPoint(warpPoint.name()) != null)
            throw new IllegalArgumentException("There is already a warp point with that name!");

        warpPoints.add(warpPoint);
        config.set(WARP_POINTS_CONFIG_PREFIX + warpPoint.name(), warpPoint);
        config.save(configFile);
    }

    /**
     * Removes a warp point from this list and updates the config file. This
     * function preforms no action if the warp point is not in this list. If there
     * are any warp pads that target this warp point, they are automatically
     * removed.
     * 
     * @param warpPoint - The warp point to remove.
     * @return A list of all warp pads that were removed as a result of deleting
     *         this warp point.
     * @throws IOException If there is an IO issue while updating the config file.
     */
    public List<WarpPad> removeWarpPoint(WarpPoint warpPoint) throws IOException {
        if (!warpPoints.contains(warpPoint))
            return new ArrayList<>();

        warpPoints.remove(warpPoint);
        config.set(WARP_POINTS_CONFIG_PREFIX + warpPoint.name(), null);
        config.save(configFile);

        var removedWarpPads = new ArrayList<WarpPad>();
        for (var warpPad : new ArrayList<>(warpPads)) {
            if (warpPad.warpPoint().equals(warpPoint.name())) {
                removeWarpPad(warpPad);
                removedWarpPads.add(warpPad);
            }
        }

        return removedWarpPads;
    }

    /**
     * Gets a read-only list of all warp points in this list.
     * 
     * @return A list of all warp points.
     */
    public List<WarpPoint> getWarpPoints() {
        return warpPoints.asReadOnly();
    }

    /**
     * Gets the warp pad with the given name.
     * 
     * @param name - The name of the warp pad.
     * @return The warp pad with the matching name, or null if there is no warp pad
     *         with the given name.
     */
    public WarpPad getWarpPad(String name) {
        for (var warpPad : warpPads) {
            if (warpPad.name().equals(name))
                return warpPad;
        }

        return null;
    }

    /**
     * Adds a new warp pad to this list and updates the config file.
     * 
     * @param warpPad - The warp pad to add.
     * @throws IllegalArgumentException If there is already a warp pad with a
     *                                  matching name.
     * @throws IllegalStateException    If the target warp point is not in this
     *                                  list.
     * @throws IOException              If there is an IO issue while updating the
     *                                  config file.
     */
    public void addWarpPad(WarpPad warpPad) throws IOException {
        if (getWarpPad(warpPad.name()) != null)
            throw new IllegalArgumentException("There is already a warp pad with that name.");

        if (getWarpPoint(warpPad.warpPoint()) == null)
            throw new IllegalStateException("The target warp point does not exist!");

        warpPads.add(warpPad);
        locationTriggers.registerLocationTrigger(warpPad.locationTrigger());

        config.set(WARP_PADS_CONFIG_PREFIX + warpPad.name(), warpPad);
        config.save(configFile);
    }

    /**
     * Removes a warp pad from this list and updates the config file. This function
     * preforms no action if the warp pad is not in this list.
     * 
     * @param warpPad - The warp pad to remove.
     * @throws IOException If there is an IO issue while updating the config file.
     */
    public void removeWarpPad(WarpPad warpPad) throws IOException {
        if (!warpPads.contains(warpPad))
            return;

        warpPads.remove(warpPad);
        locationTriggers.unregisterLocationTrigger(warpPad.locationTrigger());

        config.set(WARP_PADS_CONFIG_PREFIX + warpPad.name(), null);
        config.save(configFile);
    }

    /**
     * Gets a read-only list of all warp pads in this list.
     * 
     * @return A list of all warp pads.
     */
    public List<WarpPad> getWarpPads() {
        return warpPads.asReadOnly();
    }
}
