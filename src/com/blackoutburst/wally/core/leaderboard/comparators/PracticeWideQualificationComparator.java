package com.blackoutburst.wally.core.leaderboard.comparators;

import com.blackoutburst.wally.core.statsobject.PracticeStats;

import java.util.Comparator;

public class PracticeWideQualificationComparator implements Comparator<PracticeStats> {
    public int compare(PracticeStats b, PracticeStats a) {
        int comparator = Integer.compare(a.wideQualification, b.wideQualification);
        return comparator == 0 ? Integer.compare(a.wideQualification, b.wideQualification) : comparator;
    }
}