package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.StatsObject;

import java.util.Comparator;

public class OverallComparator implements Comparator<StatsObject> {
    public int compare(StatsObject b, StatsObject a) {
        int comparator = Float.compare(a.getOverall(), b.getOverall());
        return comparator == 0 ? Float.compare(a.getOverall(), b.getOverall()) : comparator;
    }
}