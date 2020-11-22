package comparator;

import java.util.Comparator;

import core.Player;

public class PlayerComparatorRounds implements Comparator<Player> {
	/**
	 * Set list descending order using rounds played
	 * @author Blackoutburst
	 *
	 */
    public int compare(Player a, Player b) {
        int winsComparator = Integer.valueOf(a.R).compareTo(b.R);
        return winsComparator == 0 ? Integer.valueOf(a.R).compareTo(b.R) : winsComparator;
    }
}