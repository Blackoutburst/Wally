package comparator;

import java.util.Comparator;

import core.Player;

public class PlayerComparatorTotal implements Comparator<Player> {
	/**
	 * Set list descending order using total score
	 * @author Blackoutburst
	 *
	 */
    public int compare(Player a, Player b) {
        int winsComparator = Integer.valueOf(a.total).compareTo(b.total);
        return winsComparator == 0 ? Integer.valueOf(a.total).compareTo(b.total) : winsComparator;
    }
}