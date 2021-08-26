package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorLQF implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.total).compareTo(b.total);
		return winsComparator == 0 ? Integer.valueOf(a.total).compareTo(b.total) : winsComparator;
	}
}