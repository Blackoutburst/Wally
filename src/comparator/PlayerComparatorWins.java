package comparator;

import java.util.Comparator;

import core.Player;

public class PlayerComparatorWins implements Comparator<Player> {
	/**
	 * Set list descending order using wins
	 * @author Blackoutburst
	 *
	 */
    public int compare(Player a, Player b) {
        int winsComparator = Integer.valueOf(a.W).compareTo(b.W);
        return winsComparator == 0 ? Integer.valueOf(a.W).compareTo(b.W) : winsComparator;
    }
}