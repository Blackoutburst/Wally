package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorRounds implements Comparator<LeaderboardPlayer> {

	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int comparator = Integer.compare(a.walls, b.walls);
		return comparator == 0 ? Integer.compare(a.walls, b.walls) : comparator;
	}
}