package com.blackoutburst.wally.core.leaderboard;

import com.blackoutburst.wally.core.statsobject.StatsObject;
import com.blackoutburst.wally.utils.DatabaseUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Leaderboard {

    public enum Type {
        WINS,
        WALLS,
        QUALIFICATION,
        FINALS,
        TOTAL,
        TOTAL_API,
        CHALLENGES,
        OVERALL,
        LOBBY,
        WIDE_QUALIFICATION,
        WIDE_FINALS
    }

    public final List<StatsObject> players = new ArrayList<>();
    public final String name;
    public final Comparator<StatsObject> comparator;
    public final DatabaseUtils.DataType dataType;
    public final Type type;

    public Leaderboard(@Nonnull String name, @Nonnull Comparator<StatsObject> comparator, DatabaseUtils.DataType dataType, Type type) {
        this.name = name;
        this.comparator = comparator;
        this.dataType = dataType;
        this.type = type;
    }
}
