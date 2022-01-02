package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorPQ implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int comparator = Integer.compare(a.qualification, b.qualification);
		return comparator == 0 ? Integer.compare(a.qualification, b.qualification) : comparator;
	}
}