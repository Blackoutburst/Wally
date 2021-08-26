package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorPQ implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.qualification).compareTo(b.qualification);
		return winsComparator == 0 ? Integer.valueOf(a.qualification).compareTo(b.qualification) : winsComparator;
	}
}