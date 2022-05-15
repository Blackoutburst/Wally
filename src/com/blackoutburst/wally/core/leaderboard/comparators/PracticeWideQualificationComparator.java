package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.PracticeStats;
import com.blackoutburst.wally.core.statsobject.StatsObject;

import java.util.Comparator;

public class PracticeWideQualificationComparator implements Comparator<StatsObject> {
    public int compare(StatsObject b, StatsObject a) {
        int comparator = Integer.compare(((PracticeStats)a).wideQualification, ((PracticeStats)b).wideQualification);
        return comparator == 0 ? Integer.compare(((PracticeStats)a).wideQualification, ((PracticeStats)b).wideQualification) : comparator;
    }
}