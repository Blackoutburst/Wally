package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.StatsObject;

import java.util.Comparator;

public class HypixelChallengesComparator implements Comparator<StatsObject> {
    public int compare(StatsObject b, StatsObject a) {
        int comparator = Integer.compare(((HypixelStats)a).challenges, ((HypixelStats)b).challenges);
        return comparator == 0 ? Integer.compare(((HypixelStats)a).challenges, ((HypixelStats)b).challenges) : comparator;
    }
}