package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorPOverall implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer a, PracticeLeaderboardPlayer b) {
		int winsComparator = Float.valueOf(a.overall).compareTo(b.overall);
		return winsComparator == 0 ? Float.valueOf(a.overall).compareTo(b.overall) : winsComparator;
	}
}