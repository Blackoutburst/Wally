package comparator;

import java.util.Comparator;

import core.Player;

public class PlayerComparatorQ implements Comparator<Player> {
    public int compare(Player a, Player b) {
        int winsComparator = Integer.valueOf(a.Q).compareTo(b.Q);
        return winsComparator == 0 ? Integer.valueOf(a.Q).compareTo(b.Q) : winsComparator;
    }
}