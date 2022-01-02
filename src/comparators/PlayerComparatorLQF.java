package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorLQF implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int comparator = Integer.compare(a.total, b.total);
		return comparator == 0 ? Integer.compare(a.total, b.total) : comparator;
	}
}