package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorF implements Comparator<LeaderboardPlayer> {
	
	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int comparator = Integer.compare(a.finals, b.finals);
		return comparator == 0 ? Integer.compare(a.finals, b.finals) : comparator;
	}
}