package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorF implements Comparator<LeaderboardPlayer> {
	
	public int compare(LeaderboardPlayer b, LeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.finals).compareTo(b.finals);
		return winsComparator == 0 ? Integer.valueOf(a.finals).compareTo(b.finals) : winsComparator;
	}
}