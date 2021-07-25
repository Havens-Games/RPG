package net.whg.utils;

import org.bukkit.plugin.java.JavaPlugin;

import net.whg.utils.whsculpt.WHSculptCommand;

public class UtilsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("whsculpt").setExecutor(new WHSculptCommand());
    }
}
