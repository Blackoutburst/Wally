package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.PracticeStats
import java.util.Comparator

class PracticeCreditEarnedComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as PracticeStats).creditsEarned.compareTo((b as PracticeStats).creditsEarned)
        return if (comparator == 0) a.creditsEarned.compareTo(b.creditsEarned) else comparator
    }
}