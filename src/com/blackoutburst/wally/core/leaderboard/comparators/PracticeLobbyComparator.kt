package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.PracticeStats
import java.util.Comparator

class PracticeLobbyComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as PracticeStats).lobby.compareTo((b as PracticeStats).lobby)
        return if (comparator == 0) a.lobby.compareTo(b.lobby) else comparator
    }
}