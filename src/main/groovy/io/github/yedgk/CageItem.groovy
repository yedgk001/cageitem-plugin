package io.github.yedgk

import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.UseCooldown
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material

class CageItem {
    static final def cageItem = new ItemBuilder(Material.CHAIN)
            .displayName(Component.text("Buda", NamedTextColor.YELLOW).decorate(TextDecoration.BOLD))
            .lore(
                    Component.text("Po kliknięciu PPM na przeciwnika"),
                    Component.text("tworzy z nim klatkę 1vs1 na 10 sekund"),
                    Component.text(""),
                    Component.text("Czas przeładowania to 1 minuta 20 sekund")
            )
            .data(DataComponentTypes.USE_COOLDOWN, UseCooldown.useCooldown(80).cooldownGroup(Key.key("cageitem-plugin", "cageitem")))
            .customModelData(981)
            .build()
}
