package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorRounds implements Comparator<LeaderboardPlayer> {

	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.walls).compareTo(b.walls);
		return winsComparator == 0 ? Integer.valueOf(a.walls).compareTo(b.walls) : winsComparator;
	}
}