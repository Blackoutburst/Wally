package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.PracticeStats;
import com.blackoutburst.wally.core.statsobject.StatsObject;

import java.util.Comparator;

public class PracticeLobbyComparator implements Comparator<StatsObject> {
    public int compare(StatsObject b, StatsObject a) {
        int comparator = Integer.compare(((PracticeStats)a).lobby, ((PracticeStats)b).lobby);
        return comparator == 0 ? Integer.compare(((PracticeStats)a).lobby, ((PracticeStats)b).lobby) : comparator;
    }
}