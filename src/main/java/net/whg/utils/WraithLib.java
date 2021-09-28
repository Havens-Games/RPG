package net.whg.utils;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.whg.utils.cmdformat.CommandHandler;
import net.whg.utils.events.location.CylinderLocationTrigger;
import net.whg.utils.events.location.LocationTriggerListener;
import net.whg.utils.events.location.SphereLocationTrigger;
import net.whg.utils.logging.LoggingHandler;
import net.whg.utils.warp.SpawnPoints;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpListener;
import net.whg.utils.warp.WarpPad;
import net.whg.utils.warp.WarpPoint;
import net.whg.utils.warp.cmd.spawn.SpawnCommand;
import net.whg.utils.warp.cmd.warp.WarpCommand;
import net.whg.utils.warp.cmd.warppad.WarpPadCommand;

/**
 * The Wraithaven Utils plugin is a collection of small utility commands and
 * functions that are designed to act as the primary "essentials-like" plugin on
 * the official server. It should manage a lot of the basic functions that are
 * being used like warps and anti-grief.
 */
public class WraithLib extends JavaPlugin {
    public static LoggingHandler log = new LoggingHandler(Logger.getLogger(WraithLib.class.toString()));

    /**
     * Initialize configuration serializable classes.
     */
    static {
        ConfigurationSerialization.registerClass(SphereLocationTrigger.class, "SphereLocationTrigger");
        ConfigurationSerialization.registerClass(CylinderLocationTrigger.class, "CylinderLocationTrigger");
        ConfigurationSerialization.registerClass(WarpPad.class, "WarpPad");
        ConfigurationSerialization.registerClass(WarpPoint.class, "WarpPoint");
    }

    /**
     * Sets the logging handler that is assigned to this plugin instance.
     * 
     * @param loggingHandler - The new logging handler.
     */
    private static void setLoggingHandler(LoggingHandler loggingHandler) {
        WraithLib.log = loggingHandler;
    }

    /**
     * Called when the plugin is enabled to initialize all managers, handlers, and
     * load resources.
     */
    @Override
    public void onEnable() {
        setLoggingHandler(new LoggingHandler(getLogger()));

        var locationTriggerListener = new LocationTriggerListener();
        var warpList = new WarpList(locationTriggerListener);
        var spawnPoints = new SpawnPoints();

        loadCommand("warp", new WarpCommand(warpList));
        loadCommand("warppad", new WarpPadCommand(warpList));
        loadCommand("spawn", new SpawnCommand(spawnPoints));

        registerEvents(locationTriggerListener);
        registerEvents(new WarpListener(warpList));
    }

    /**
     * Loads a command by name and registers the command executor.
     * 
     * @param commandName - The command name.
     * @param handler     - The command handler.
     */
    private void loadCommand(String commandName, CommandHandler handler) {
        WraithLib.log.logInfo("Loading %s command.", commandName);
        getCommand(commandName).setExecutor(handler);
    }

    /**
     * Loads an event listener and registers it with Bukkit.
     * 
     * @param listener - The event listener to register.
     */
    private void registerEvents(Listener listener) {
        WraithLib.log.logInfo("Registered event listeners for %s.", listener.getClass().getSimpleName());
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    /**
     * Called when the plugin is disabled to clear all resources.
     */
    @Override
    public void onDisable() {
        WraithLib.log.logInfo("Unregistering all event listeners.");
        HandlerList.unregisterAll(this);

        WraithLib.log.logInfo("Disabled WraithLib plugin.");
        setLoggingHandler(new LoggingHandler(Logger.getLogger(WraithLib.class.toString())));
    }
}
