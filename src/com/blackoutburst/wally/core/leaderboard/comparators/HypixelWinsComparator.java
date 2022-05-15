package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.StatsObject;

import java.util.Comparator;

public class HypixelWinsComparator implements Comparator<StatsObject> {
    public int compare(StatsObject b, StatsObject a) {
        int comparator = Integer.compare(((HypixelStats)a).wins, ((HypixelStats)b).wins);
        return comparator == 0 ? Integer.compare(((HypixelStats)a).wins, ((HypixelStats)b).wins) : comparator;
    }
}