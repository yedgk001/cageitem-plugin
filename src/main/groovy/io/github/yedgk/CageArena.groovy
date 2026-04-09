package io.github.yedgk

import org.bukkit.Location

class CageArena {
    Location center
    double radius
    List<UUID> participants

    CageArena(Location center, double radius, List<UUID> participants) {
        this.center = center
        this.radius = radius
        this.participants = participants
    }

    def inside(Location loc) {
        return loc.world == center.world && Math.abs(loc.x - center.x) <= radius && Math.abs(loc.z - center.z) <= radius
    }
}