package comparators;

import java.util.Comparator;

import utils.LeaderboardPlayer;

public class PlayerComparatorOverall implements Comparator<LeaderboardPlayer> {
	
	public int compare(LeaderboardPlayer a, LeaderboardPlayer b) {
		int winsComparator = Float.valueOf(a.overall).compareTo(b.overall);
		return winsComparator == 0 ? Float.valueOf(a.overall).compareTo(b.overall) : winsComparator;
	}
}