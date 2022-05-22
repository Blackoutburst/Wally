package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import java.util.Comparator

class OverallComparator : Comparator<StatsObject> {
    override fun compare(a: StatsObject, b: StatsObject): Int {
        val comparator = a.overall.compareTo(b.overall)
        return if (comparator == 0) a.overall.compareTo(b.overall) else comparator
    }
}