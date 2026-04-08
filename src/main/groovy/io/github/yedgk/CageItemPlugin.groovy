package io.github.yedgk

import org.bukkit.plugin.java.JavaPlugin

class CageItemPlugin extends JavaPlugin {

    @Override
    void onEnable() {
        getServer().getPluginManager().registerEvents(new CageItemListener(this), this)
    }
}
