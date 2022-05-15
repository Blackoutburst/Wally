package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.StatsObject;

import java.util.Comparator;

public class QualificationComparator implements Comparator<StatsObject> {
    public int compare(StatsObject b, StatsObject a) {
        int comparator = Integer.compare(a.getQualification(), b.getQualification());
        return comparator == 0 ? Integer.compare(a.getQualification(), b.getQualification()) : comparator;
    }
}