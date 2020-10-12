package comparator;

import java.util.Comparator;

import core.Player;

public class PlayerComparatorRounds implements Comparator<Player> {
    public int compare(Player a, Player b) {
        int winsComparator = Integer.valueOf(a.R).compareTo(b.R);
        return winsComparator == 0 ? Integer.valueOf(a.R).compareTo(b.R) : winsComparator;
    }
}