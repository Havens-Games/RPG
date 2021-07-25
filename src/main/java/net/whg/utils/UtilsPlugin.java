package net.whg.utils;

import org.bukkit.plugin.java.JavaPlugin;

import net.whg.utils.warp.WarpCommand;
import net.whg.utils.warp.WarpList;
import net.whg.utils.warp.WarpPadCommand;
import net.whg.utils.whsculpt.WHSculptCommand;

public class UtilsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        var warpList = new WarpList(this);

        getCommand("whsculpt").setExecutor(new WHSculptCommand());
        getCommand("warp").setExecutor(new WarpCommand(warpList));
        getCommand("warppad").setExecutor(new WarpPadCommand(warpList));
    }
}
