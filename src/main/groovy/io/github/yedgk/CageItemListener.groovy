package io.github.yedgk

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.Plugin

import java.util.concurrent.CopyOnWriteArrayList

class CageItemListener implements Listener {

    private final Plugin plugin
    private final List<CageArena> activeCages = new CopyOnWriteArrayList<>()

    CageItemListener(Plugin plugin) {
        this.plugin = plugin
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
        if (player.hasCooldown(CageItem.cageItem)) {
            player.sendActionBar(Component.text("Przedmiot jest jeszcze niedostępny!").color(NamedTextColor.DARK_RED))
            return
        }
        def target = (Player) event.rightClicked
        createCage(player, target)
    }

    private void createCage(Player p1, Player p2) {
        def center = p1.location
        double size = 10.0D
        double radius = size / 2.0D

        def arena = new CageArena(center, radius, [p1.uniqueId, p2.uniqueId])
        activeCages.add(arena)

        [p1, p2].each { p ->
            def pb = Bukkit.createWorldBorder()
            pb.center = center
            pb.size = size
            pb.warningDistance = 0
            p.worldBorder = pb
        }
        p1.setCooldown(CageItem.cageItem, 20 * 80)
        p1.showTitle(Title.title(Component.empty(), Component.text("Uwięziłeś gracza!", NamedTextColor.GOLD)))
        def particleTask = Bukkit.scheduler.runTaskTimer(plugin, {
            def world = center.world
            def y = center.y
            for (double i = -radius; i <= radius; i += 1.0) {
                world.spawnParticle(Particle.FLAME, center.x + i, y + 1, center.z - radius, 1, 0, 0, 0, 0)
                world.spawnParticle(Particle.FLAME, center.x + i, y + 1, center.z + radius, 1, 0, 0, 0, 0)
                world.spawnParticle(Particle.FLAME, center.x - radius, y + 1, center.z + i, 1, 0, 0, 0, 0)
                world.spawnParticle(Particle.FLAME, center.x + radius, y + 1, center.z + i, 1, 0, 0, 0, 0)
            }
        }, 0L, 10L)
        Bukkit.scheduler.runTaskLater(plugin, {
            particleTask.cancel()
            activeCages.remove(arena)
            [p1, p2].each { it.worldBorder = it.world.worldBorder }
        }, 20L * 10)
    }

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedBlock()) return
        def player = event.player
        def to = event.to
        activeCages.each {
            if (!it.participants.contains(player.uniqueId) && it.isInside(to)) {
                event.cancelled = true
            }
        }
    }

    @EventHandler
    void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.cause != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return
        def player = event.player
        def to = event.to

        activeCages.each {
            boolean isParticipant = it.participants.contains(player.uniqueId)
            boolean targetInside = it.isInside(to)
            if ((isParticipant && !targetInside) || (!isParticipant && targetInside)) event.cancelled = true
        }
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