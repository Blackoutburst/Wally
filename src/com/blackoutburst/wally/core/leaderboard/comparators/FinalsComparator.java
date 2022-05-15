package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.StatsObject;

import java.util.Comparator;

public class FinalsComparator implements Comparator<StatsObject> {
    public int compare(StatsObject b, StatsObject a) {
        int comparator = Integer.compare(a.getFinals(), b.getFinals());
        return comparator == 0 ? Integer.compare(a.getFinals(), b.getFinals()) : comparator;
    }
}