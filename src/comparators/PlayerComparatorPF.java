package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorPF implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.finals).compareTo(b.finals);
		return winsComparator == 0 ? Integer.valueOf(a.finals).compareTo(b.finals) : winsComparator;
	}
}