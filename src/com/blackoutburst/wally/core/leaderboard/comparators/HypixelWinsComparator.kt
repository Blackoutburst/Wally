package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.HypixelStats
import java.util.Comparator

class HypixelWinsComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as HypixelStats).wins.compareTo((b as HypixelStats).wins)
        return if (comparator == 0) a.wins.compareTo(b.wins) else comparator
    }
}