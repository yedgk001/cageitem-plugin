package io.github.yedgk

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.Plugin

class CageListener implements Listener {
    private final Plugin plugin
    private final CageService cageService

    CageListener(Plugin plugin, CageService cageService) {
        this.plugin = plugin
        this.cageService = cageService
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        if (!event.itemInHand.isSimilar(CageItem.cageItem)) return
        event.cancelled = true
    }

    @EventHandler
    void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (!event.player.inventory.itemInMainHand.isSimilar(CageItem.cageItem)) return
        if (!(event.rightClicked instanceof Player)) return
        def player = event.player
        if (player.hasCooldown(CageItem.cageItem)) {
            player.sendActionBar(
                    Component.text("Przedmiot jest jeszcze niedostępny! ( ${(Integer) player.getCooldown(Material.CHAIN) / 20}s )")
                            .decoration(TextDecoration.ITALIC, false)
                            .color(NamedTextColor.RED))
            return
        }
        player.setCooldown(CageItem.cageItem, 1600)
        cageService.createCage(player, event.rightClicked as Player)
    }

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedBlock()) return
        def player = event.player
        def to = event.to
        if (cageService.cageList().any { it.participants.contains(player.uniqueId) && !it.inside(to) }) event.cancelled = true
    }

    @EventHandler
    void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.cause != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return
        def player = event.player
        def to = event.to
        if (cageService.cageList().any { it.participants.contains(player.uniqueId) && !it.inside(to) }) event.cancelled = true
    }

    @EventHandler
    void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.entity instanceof Player)) return
        def player = (Player) event.entity
        if (cageService.cageList().any { it.participants.contains(player.uniqueId) }) {
            if (player.inventory.chestplate?.type == Material.ELYTRA) {
                event.cancelled = true
            }
        }
    }
}