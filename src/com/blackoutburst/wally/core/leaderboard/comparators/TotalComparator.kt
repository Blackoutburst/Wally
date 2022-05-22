package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import java.util.Comparator

class TotalComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = a.total.compareTo(b.total)
        return if (comparator == 0) a.total.compareTo(b.total) else comparator
    }
}