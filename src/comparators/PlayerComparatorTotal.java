package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorTotal implements Comparator<LeaderboardPlayer> {
	
	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.total).compareTo(b.total);
		return winsComparator == 0 ? Integer.valueOf(a.total).compareTo(b.total) : winsComparator;
	}
}