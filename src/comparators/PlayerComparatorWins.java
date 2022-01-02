package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorWins implements Comparator<LeaderboardPlayer> {

	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int comparator = Integer.compare(a.wins, b.wins);
		return comparator == 0 ? Integer.compare(a.wins, b.wins) : comparator;
	}
}