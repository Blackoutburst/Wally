package comparators;

import utils.PracticeLeaderboardPlayer;

import java.util.Comparator;

public class PlayerComparatorWF implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int comparator = Integer.compare(a.wideFinals, b.wideFinals);
		return comparator == 0 ? Integer.compare(a.wideFinals, b.wideFinals) : comparator;
	}
}