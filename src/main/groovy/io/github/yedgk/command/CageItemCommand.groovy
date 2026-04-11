package io.github.yedgk.command

import io.github.yedgk.CageItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull

class CageItemCommand extends Command {
    CageItemCommand() {
        super("cageitem", "Nadaje do ekwipunku gracza Budę..", "", ["dajbude"])
    }

    @Override
    boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender instanceof Player) {
            sender.sendMessage("Komenda tylko dla gracza..")
            return false
        }
        def player = sender as Player
        player.inventory.addItem(CageItem.cageItem)
        player.sendMessage(
                Component.text("Nadano do ekwipunku Bude..")
                        .color(NamedTextColor.LIGHT_PURPLE)
                        .decoration(TextDecoration.ITALIC, false))
        return true
    }
}
