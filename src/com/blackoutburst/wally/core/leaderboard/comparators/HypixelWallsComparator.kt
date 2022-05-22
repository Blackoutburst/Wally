package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.HypixelStats
import java.util.Comparator

class HypixelWallsComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as HypixelStats).walls.compareTo((b as HypixelStats).walls)
        return if (comparator == 0) a.walls.compareTo(b.walls) else comparator
    }
}