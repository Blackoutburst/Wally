package com.blackoutburst.wally.core.stats;

import javax.annotation.Nonnull;

/**
 * This class is used to serialize data using GSON
 */
public class HypixelStats {

    private final String wins;
    private final String walls;
    private final String q;
    private final String f;

    public HypixelStats(@Nonnull final String wins, @Nonnull final String walls, @Nonnull final String q, @Nonnull final String f) {
        this.wins = wins;
        this.walls = walls;
        this.q = q;
        this.f = f;
    }

    public String getWins() {
        return wins;
    }

    public String getWalls() {
        return walls;
    }

    public String getQ() {
        return q;
    }

    public String getF() {
        return f;
    }
}
