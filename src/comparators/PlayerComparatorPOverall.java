package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorPOverall implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer a, PracticeLeaderboardPlayer b) {
		int comparator = Float.compare(a.overall, b.overall);
		return comparator == 0 ? Float.compare(a.overall, b.overall) : comparator;
	}
}