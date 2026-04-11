package io.github.yedgk

import org.bukkit.plugin.java.JavaPlugin

class CageItemPlugin extends JavaPlugin {
    @Override
    void onEnable() {
        def cageService = new CageService()
        server.pluginManager.registerEvents(new CageListener(this, cageService), this)
        registerCommand("cageitem", new CageCommand())
    }
}
