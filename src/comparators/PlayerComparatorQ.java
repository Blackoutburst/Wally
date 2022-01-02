package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorQ implements Comparator<LeaderboardPlayer> {

	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int comparator = Integer.compare(a.qualification, b.qualification);
		return comparator == 0 ? Integer.compare(a.qualification, b.qualification) : comparator;
	}
}