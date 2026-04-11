package io.github.yedgk

import org.bukkit.Location

class Cage {
    Location center
    double radius
    List<UUID> participants

    Cage(Location center, double radius, List<UUID> participants) {
        this.center = center
        this.radius = radius
        this.participants = participants
    }

    def inside(Location loc) {
        return loc.world == center.world && Math.abs(loc.x - center.x) <= radius && Math.abs(loc.z - center.z) <= radius
    }
}