package com.blackoutburst.wally.core.leaderboard.comparators

import com.blackoutburst.wally.core.statsobject.StatsObject
import java.util.Comparator

class QualificationComparator : Comparator<StatsObject> {
    override fun compare(b: StatsObject, a: StatsObject): Int {
        val comparator = a.qualification.compareTo(b.qualification)
        return if (comparator == 0) a.qualification.compareTo(b.qualification) else comparator
    }
}