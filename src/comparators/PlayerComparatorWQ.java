package comparators;

import java.util.Comparator;

import utils.PracticeLeaderboardPlayer;

public class PlayerComparatorWQ implements Comparator<PracticeLeaderboardPlayer> {
	
	public int compare(PracticeLeaderboardPlayer b, PracticeLeaderboardPlayer a) {
		int winsComparator = Integer.valueOf(a.wideQualification).compareTo(b.wideQualification);
		return winsComparator == 0 ? Integer.valueOf(a.wideQualification).compareTo(b.wideQualification) : winsComparator;
	}
}