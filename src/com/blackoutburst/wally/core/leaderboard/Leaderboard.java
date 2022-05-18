package com.blackoutburst.wally.core.leaderboard;

import com.blackoutburst.wally.core.statsobject.StatsObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {

    public final List<StatsObject> players = new ArrayList<>();
    public final String name;
    public final Comparator comparator;

    public Leaderboard(@Nonnull String name, @Nonnull Comparator comparator) {
        this.name = name;
        this.comparator = comparator;
    }
}
