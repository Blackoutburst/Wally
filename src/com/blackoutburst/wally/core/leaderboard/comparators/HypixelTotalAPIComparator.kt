package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.HypixelStats
import java.util.Comparator

class HypixelTotalAPIComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as HypixelStats).totalAPI.compareTo((b as HypixelStats).totalAPI)
        return if (comparator == 0) a.totalAPI.compareTo(b.totalAPI) else comparator
    }
}