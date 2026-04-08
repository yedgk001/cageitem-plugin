package io.github.yedgk

import org.bukkit.plugin.java.JavaPlugin

class CaseItemPlugin extends JavaPlugin {

    @Override
    void onEnable() {
        getServer().getPluginManager().registerEvents(new CaseItemListener(this), this)
    }
}
