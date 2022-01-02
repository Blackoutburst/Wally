package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorWQ implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int comparator = Integer.compare(a.wideQualification, b.wideQualification);
		return comparator == 0 ? Integer.compare(a.wideQualification, b.wideQualification) : comparator;
	}
}