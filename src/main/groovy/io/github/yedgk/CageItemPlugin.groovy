package io.github.yedgk

import io.github.yedgk.command.CageItemCommand
import io.github.yedgk.listener.CageListener
import io.github.yedgk.service.CageService
import org.bukkit.plugin.java.JavaPlugin

class CageItemPlugin extends JavaPlugin {
    @Override
    void onEnable() {
        server.pluginManager.registerEvents(new CageListener(this, new CageService()), this)
        server.commandMap.register("cageitem-plugin", new CageItemCommand())
    }
}
