package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.core.statsobject.PracticeStats
import java.util.Comparator

class PracticeWideQualificationComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = (a as PracticeStats).wideQualification.compareTo((b as PracticeStats).wideQualification)
        return if (comparator == 0) a.wideQualification.compareTo(b.wideQualification) else comparator
    }
}