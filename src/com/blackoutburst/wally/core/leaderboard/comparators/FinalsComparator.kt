package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import java.util.Comparator

class FinalsComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = a.finals.compareTo(b.finals)
        return if (comparator == 0) a.finals.compareTo(b.finals) else comparator
    }
}