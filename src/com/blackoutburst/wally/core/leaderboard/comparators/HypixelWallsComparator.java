package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.HypixelStats;

import java.util.Comparator;

public class HypixelWallsComparator implements Comparator<HypixelStats> {
    public int compare(HypixelStats b, HypixelStats a) {
        int comparator = Integer.compare(a.walls, b.walls);
        return comparator == 0 ? Integer.compare(a.walls, b.walls) : comparator;
    }
}