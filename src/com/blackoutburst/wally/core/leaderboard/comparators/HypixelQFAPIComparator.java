package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.HypixelStats;

import java.util.Comparator;

public class HypixelQFAPIComparator implements Comparator<HypixelStats> {
    public int compare(HypixelStats b, HypixelStats a) {
        int comparator = Integer.compare(a.QFAPI, b.QFAPI);
        return comparator == 0 ? Integer.compare(a.QFAPI, b.QFAPI) : comparator;
    }
}