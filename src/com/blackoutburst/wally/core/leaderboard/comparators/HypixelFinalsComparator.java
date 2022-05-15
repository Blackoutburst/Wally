package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.HypixelStats;

import java.util.Comparator;

public class HypixelFinalsComparator implements Comparator<HypixelStats> {
    public int compare(HypixelStats b, HypixelStats a) {
        int comparator = Integer.compare(a.finals, b.finals);
        return comparator == 0 ? Integer.compare(a.finals, b.finals) : comparator;
    }
}