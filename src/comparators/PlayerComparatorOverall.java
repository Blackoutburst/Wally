package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorOverall implements Comparator<LeaderboardPlayer> {
	
	public int compare(LeaderboardPlayer a, LeaderboardPlayer b) {
		int comparator = Float.compare(a.overall, b.overall);
		return comparator == 0 ? Float.compare(a.overall, b.overall) : comparator;
	}
}