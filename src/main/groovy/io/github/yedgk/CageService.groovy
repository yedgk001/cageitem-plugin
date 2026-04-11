package io.github.yedgk

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.entity.Player

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CageService {

    private final def cageList = []
    private final def executor = Executors.newSingleThreadScheduledExecutor();

    def createCage(Player player, Player target) {
        def center = player.location
        def cage = new Cage(center, 5, [player.uniqueId, target.uniqueId])
        cageList.add(cage)

        cage.participants.collect { Bukkit.getPlayer(it) }.each {
            def worldborder = Bukkit.createWorldBorder()
            worldborder.center = center
            worldborder.size = 10
            worldborder.warningDistance = 0
            it.worldBorder = worldborder
        }
        player.showTitle(Title.title(
                Component.text("Uwięziłeś gracza")
                        .color(NamedTextColor.WHITE)
                        .decoration(TextDecoration.ITALIC, false),
                Component.text("$target.name")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(NamedTextColor.LIGHT_PURPLE)))
        executor.schedule(new Runnable() {
            @Override
            void run() {
                cage.participants.collect { Bukkit.getPlayer(it) }.each {
                    it.worldBorder = it.world.worldBorder
                }
                cageList.remove(cage)
            }
        }, 10, TimeUnit.SECONDS)
    }

    List<Cage> cageList() {
        Collections.unmodifiableList(cageList) as List<Cage>
    }
}
