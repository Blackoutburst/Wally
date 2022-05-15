package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.StatsObject;

import java.util.Comparator;

public class TotalComparator implements Comparator<StatsObject> {
    public int compare(StatsObject b, StatsObject a) {
        int comparator = Integer.compare(a.getTotal(), b.getTotal());
        return comparator == 0 ? Integer.compare(a.getTotal(), b.getTotal()) : comparator;
    }
}