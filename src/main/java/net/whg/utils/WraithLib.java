package net.whg.utils;

import org.bukkit.plugin.java.JavaPlugin;

import net.whg.utils.warp.WarpCommand;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpListener;
import net.whg.utils.warp.WarpPadCommand;

/**
 * The Wraithaven Utils plugin is a collection of small utility commands and
 * functions that are designed to act as the primary "essentials-like" plugin on
 * the official server. It should manage a lot of the basic functions that are
 * being used like warps and anti-grief.
 */
public class WraithLib extends JavaPlugin {
    @Override
    public void onEnable() {
        var warpList = new WarpList(this);

        getCommand("warp").setExecutor(new WarpCommand(warpList));
        getCommand("warppad").setExecutor(new WarpPadCommand(warpList));

        var pm = getServer().getPluginManager();
        pm.registerEvents(new WarpListener(warpList), this);
    }
}
