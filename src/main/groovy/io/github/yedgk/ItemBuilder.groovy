package io.github.yedgk

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class ItemBuilder {
    private final ItemStack itemStack
    private final ItemMeta itemMeta

    ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1)
        this.itemMeta = this.itemStack.itemMeta
    }

    ItemBuilder displayName(Component displayName) {
        this.itemMeta.displayName(displayName)
        return this
    }

    ItemBuilder lore(Component... lore) {
        this.itemMeta.lore(List.of(lore))
        return this
    }

    ItemBuilder customModelData(int customModelData) {
        this.itemMeta.customModelData = customModelData
        return this
    }

    ItemStack build() {
        this.itemStack.itemMeta = this.itemMeta
        return this.itemStack
    }
}
