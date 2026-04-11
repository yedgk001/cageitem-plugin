package io.github.yedgk

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

class CageCommand implements BasicCommand {
    @Override
    void execute(CommandSourceStack commandSourceStack, String[] args) {
        def player = commandSourceStack.sender as Player
        player.inventory.addItem(CageItem.cageItem)
        player.sendMessage(
                Component.text("Pomyślnie otrzymałeś do ekwipunku Bude!")
                        .color(NamedTextColor.WHITE)
                        .decoration(TextDecoration.ITALIC, false))
    }

    @Override
    String permission() {
        return "kosmosgame.cageitem"
    }
}
