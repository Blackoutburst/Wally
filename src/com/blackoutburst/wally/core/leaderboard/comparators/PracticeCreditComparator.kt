package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.PracticeStats
import java.util.Comparator

class PracticeCreditComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as PracticeStats).credits.compareTo((b as PracticeStats).credits)
        return if (comparator == 0) a.credits.compareTo(b.credits) else comparator
    }
}