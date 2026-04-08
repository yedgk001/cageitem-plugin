package io.github.yedgk

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityToggleGlideEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.Plugin

class CageItemListener implements Listener {

    private final Plugin plugin
    private final def cage = new HashSet<UUID>()

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
        if (!event.rightClicked instanceof Player) return
        def player = event.player
        if (player.hasCooldown(CageItem.cageItem)) {
            player.sendActionBar(Component.text("Masz jeszcze cooldown na użycie tego przedmiotu!").color(NamedTextColor.DARK_RED))
            return
        }
        def target = (Player) event.rightClicked
        duel(player, target)
    }

    private void duel(Player p1, Player p2) {
        Bukkit.onlinePlayers.each {
            if (it == p1 || it == p2) return
            p1.hidePlayer(plugin, it)
            p2.hidePlayer(plugin, it)
        }
        borderWithRunnable(p1, p2)
        p1.setCooldown(CageItem.cageItem, 20 * 80)
        p1.showTitle(Title.title(Component.empty(), Component.text("Użyłeś eventowego przedmiotu! (Buda)")))
        cage.add(p1.uniqueId)
        cage.add(p2.uniqueId)
    }

    private void borderWithRunnable(Player p1, Player p2) {
        def world = p1.world
        def border = world.worldBorder
        def center = p1.location
        [p1, p2].each {
            def pb = Bukkit.createWorldBorder()
            pb.center = center
            pb.size = 10
            pb.warningDistance = 0
            pb.damageAmount = 0
            it.worldBorder = pb
        }
        Bukkit.scheduler.runTaskLater(plugin, {
            [p1, p2].each {
                it.worldBorder = border
                cage.remove(it.uniqueId)
            }
        }, 20L * 10)
    }

    @EventHandler
    void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.entity instanceof Player)) return
        def player = (Player) event.entity
        if (!cage.contains(player.uniqueId)) return
        if (player.inventory.chestplate.type != Material.ELYTRA) return
        event.cancelled = true
    }

    @EventHandler
    void onPlayerTeleport(PlayerTeleportEvent event) {
        if (!cage.contains(event.player.uniqueId)) return
        if (event.cause != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return
        event.cancelled = true
    }
}
