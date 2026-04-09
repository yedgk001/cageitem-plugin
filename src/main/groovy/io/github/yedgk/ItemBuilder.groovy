package io.github.yedgk


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

    ItemBuilder displayName(Component name) {
        meta.displayName(name)
        this
    }

    ItemBuilder lore(Component... lines) {
        meta.lore(lines as List)
        this
    }

    ItemBuilder customModelData(int cmd) {
        meta.customModelData = cmd
        this
    }

    ItemStack build() {
        itemStack.itemMeta = meta
        itemStack
    }
}