package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.HypixelStats;

import java.util.Comparator;

public class HypixelWinsComparator implements Comparator<HypixelStats> {
    public int compare(HypixelStats b, HypixelStats a) {
        int comparator = Integer.compare(a.wins, b.wins);
        return comparator == 0 ? Integer.compare(a.wins, b.wins) : comparator;
    }
}