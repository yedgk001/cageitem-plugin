package io.github.yedgk

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material

class CageItem {
    static final def cageItem = new ItemBuilder(Material.RED_DYE)
            .displayName(Component.text("Buda", NamedTextColor.YELLOW).decorate(TextDecoration.BOLD))
            .lore(
                    Component.text("Po kliknięciu PPM na przeciwnika", NamedTextColor.GRAY)
                            .decorate(TextDecoration.BOLD),
                    Component.text("tworzy z nim klatkę 1vs1 ")
                            .color(NamedTextColor.GRAY)
                            .decorate(TextDecoration.BOLD)
                            .append(Component.text("10 sekund", NamedTextColor.WHITE).decorate(TextDecoration.BOLD)),
                    Component.text(""),
                    Component.text("Cooldown ")
                            .color(NamedTextColor.GRAY)
                            .decorate(TextDecoration.BOLD)
                            .append(Component.text("1 minuta 20 sekund", NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
            )
            .customModelData(1001)
            .build()
}
