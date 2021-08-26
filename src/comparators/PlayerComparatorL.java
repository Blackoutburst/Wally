package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorL implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.lobby).compareTo(b.lobby);
		return winsComparator == 0 ? Integer.valueOf(a.lobby).compareTo(b.lobby) : winsComparator;
	}
}