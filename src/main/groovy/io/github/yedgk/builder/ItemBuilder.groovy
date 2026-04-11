package io.github.yedgk.builder

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ItemBuilder {
    private final ItemStack itemStack
    private final ItemMeta meta

    ItemBuilder(Material material) {
        itemStack = new ItemStack(material, 1)
        meta = itemStack.itemMeta
    }

    ItemBuilder displayName(Component displayName) {
        meta.displayName(displayName)
        this
    }

    ItemBuilder lore(Component... componentLore) {
        meta.lore(componentLore as List)
        this
    }

    ItemBuilder customModelData(int customModelData) {
        meta.customModelData = customModelData
        this
    }

    ItemStack build() {
        itemStack.itemMeta = meta
        itemStack
    }
}