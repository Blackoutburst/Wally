package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorPF implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int comparator = Integer.compare(a.finals, b.finals);
		return comparator == 0 ? Integer.compare(a.finals, b.finals) : comparator;
	}
}