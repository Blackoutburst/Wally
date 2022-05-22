package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.HypixelStats
import java.util.Comparator

class HypixelChallengesComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as HypixelStats).challenges.compareTo((b as HypixelStats).challenges)
        return if (comparator == 0) a.challenges.compareTo(b.challenges) else comparator
    }
}