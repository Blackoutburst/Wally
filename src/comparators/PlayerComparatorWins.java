package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorWins implements Comparator<LeaderboardPlayer> {

	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.wins).compareTo(b.wins);
		return winsComparator == 0 ? Integer.valueOf(a.wins).compareTo(b.wins) : winsComparator;
	}
}