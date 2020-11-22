package comparator;

import java.util.Comparator;

import core.Player;

public class PlayerComparatorF implements Comparator<Player> {
	/**
	 * Set list descending order using finals score
	 * @author Blackoutburst
	 *
	 */
    public int compare(Player a, Player b) {
        int winsComparator = Integer.valueOf(a.F).compareTo(b.F);
        return winsComparator == 0 ? Integer.valueOf(a.F).compareTo(b.F) : winsComparator;
    }
}