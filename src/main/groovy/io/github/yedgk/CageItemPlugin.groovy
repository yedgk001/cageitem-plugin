package io.github.yedgk

import org.bukkit.plugin.java.JavaPlugin

class CageItemPlugin extends JavaPlugin {

    @Override
    void onEnable() {
        server.pluginManager.registerEvents(new CageItemListener(this), this)
    }
}
