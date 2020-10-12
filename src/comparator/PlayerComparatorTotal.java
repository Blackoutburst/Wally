package comparator;

import java.util.Comparator;

import core.Player;

public class PlayerComparatorTotal implements Comparator<Player> {
    public int compare(Player a, Player b) {
        int winsComparator = Integer.valueOf(a.total).compareTo(b.total);
        return winsComparator == 0 ? Integer.valueOf(a.total).compareTo(b.total) : winsComparator;
    }
}