package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorTotal implements Comparator<LeaderboardPlayer> {
	
	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int comparator = Integer.compare(a.total, b.total);
		return comparator == 0 ? Integer.compare(a.total, b.total) : comparator;
	}
}