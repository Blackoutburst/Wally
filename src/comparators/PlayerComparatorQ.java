package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorQ implements Comparator<LeaderboardPlayer> {

	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.qualification).compareTo(b.qualification);
		return winsComparator == 0 ? Integer.valueOf(a.qualification).compareTo(b.qualification) : winsComparator;
	}
}