package io.github.yedgk

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull

class CageItemCommand extends Command {
    protected CageItemCommand() {
        super("command", "", "", ["dajbude"])
    }

    @Override
    boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!sender instanceof Player) {
            sender.sendMessage("Ta komenda jest tylko dla gracza!")
            return false
        }
        def player = sender as Player
        player.inventory.addItem(CageItem.cageItem)
        player.sendMessage(
                Component.text("Pomyślnie otrzymałeś do ekwipunku Bude!")
                        .color(NamedTextColor.WHITE)
                        .decoration(TextDecoration.ITALIC, false))
        return true
    }
}
