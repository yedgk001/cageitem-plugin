//file:noinspection GrMethodMayBeStatic
package io.github.yedgk

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin

class CageItemListener implements Listener {
    private final Plugin plugin
    private final List<CageArena> activeCages = []

    CageItemListener(Plugin plugin) {
        this.plugin = plugin
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        if (!event.itemInHand.isSimilar(CageItem.cageItem)) return
        event.cancelled = true
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        event.player.inventory.addItem(CageItem.cageItem)
    }

    @EventHandler
    void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (!event.player.inventory.itemInMainHand.isSimilar(CageItem.cageItem)) return
        if (!(event.rightClicked instanceof Player)) return
        def player = event.player
        if (System.currentTimeMillis() - player.persistentDataContainer.get(NamespacedKey.minecraft("cageitem"), PersistentDataType.LONG) <= 80000) {
            player.sendActionBar(Component.text("Przedmiot jest jeszcze niedostępny!")
                    .decoration(TextDecoration.ITALIC, false)
                    .color(NamedTextColor.RED))
            return
        }
        player.persistentDataContainer.set(NamespacedKey.minecraft("cageitem"), PersistentDataType.LONG, System.currentTimeMillis())
        createCage(player, (Player) event.rightClicked)
    }

    private void createCage(Player p1, Player p2) {
        def center = p1.location
        double size = 10
        double radius = size / 2
        def arena = new CageArena(center, radius, [p1.uniqueId, p2.uniqueId])
        activeCages.add(arena)
        [p1, p2].each {
            def worldborder = Bukkit.createWorldBorder()
            worldborder.center = center
            worldborder.size = size
            worldborder.warningDistance = 0
            it.worldBorder = worldborder
        }
        p1.showTitle(Title.title(Component.text("Uwięziłeś gracza")
                .color(NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false),
                Component.text("$p2.name")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(NamedTextColor.LIGHT_PURPLE)))
        Bukkit.scheduler.runTaskLater(plugin, {
            activeCages.remove(arena)
            [p1, p2].each { it.worldBorder = it.world.worldBorder }
        }, 200)
    }

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedBlock()) return
        def player = event.player
        def to = event.to
        activeCages.each {
            if (!it.participants.contains(player.uniqueId) && it.inside(to)) {
                event.cancelled = true
            }
        }
    }

    @EventHandler
    void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.cause != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return
        def player = event.player
        def to = event.to
        if (activeCages.any { it.participants.contains(player.uniqueId) && it.inside(to) }) event.cancelled = true
    }

    @EventHandler
    void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.entity instanceof Player)) return
        def player = (Player) event.entity
        if (activeCages.any { it.participants.contains(player.uniqueId) }) {
            if (player.inventory.chestplate?.type == Material.ELYTRA) {
                event.cancelled = true
            }
        }
    }
}