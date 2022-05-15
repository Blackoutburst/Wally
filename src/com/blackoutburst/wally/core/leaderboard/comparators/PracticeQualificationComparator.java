package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.PracticeStats;

import java.util.Comparator;

public class PracticeQualificationComparator implements Comparator<PracticeStats> {
    public int compare(PracticeStats b, PracticeStats a) {
        int comparator = Integer.compare(a.qualification, b.qualification);
        return comparator == 0 ? Integer.compare(a.qualification, b.qualification) : comparator;
    }
}