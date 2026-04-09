package io.github.yedgk


import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material

class CageItem {
    static final def cageItem = new ItemBuilder(Material.CHAIN)
            .displayName(Component.text("Buda").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.text("Po kliknięciu PPM na przeciwnika").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                    Component.text("tworzy z nim klatkę 1vs1 na 10 sekund").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
                    Component.text(""),
                    Component.text("Czas przeładowania to ", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false).append(Component.text("1 minuta 20 sekund").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false))
            )
            .customModelData(981)
            .build()
}
