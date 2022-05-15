package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.PracticeStats;

import java.util.Comparator;

public class PracticeFinalsComparator implements Comparator<PracticeStats> {
    public int compare(PracticeStats b, PracticeStats a) {
        int comparator = Integer.compare(a.finals, b.finals);
        return comparator == 0 ? Integer.compare(a.finals, b.finals) : comparator;
    }
}