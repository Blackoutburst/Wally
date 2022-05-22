package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.PracticeStats
import java.util.Comparator

class PracticeWideFinalsComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as PracticeStats).wideFinals.compareTo((b as PracticeStats).wideFinals)
        return if (comparator == 0) a.wideFinals.compareTo(b.wideFinals) else comparator
    }
}