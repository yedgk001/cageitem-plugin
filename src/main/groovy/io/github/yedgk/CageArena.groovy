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

    boolean isInside(Location loc) {
        if (loc.world != center.world) return false
        double dx = Math.abs(loc.x - center.x)
        double dz = Math.abs(loc.z - center.z)
        return dx <= radius && dz <= radius
    }
}