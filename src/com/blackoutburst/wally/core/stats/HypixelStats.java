package com.blackoutburst.wally.core.stats;

/**
 * This class is used to serialize data using GSON
 */
public class HypixelStats {

    private final String wins;
    private final String walls;
    private final String q;
    private final String f;

    public HypixelStats(String wins, String walls, String q, String f) {
        this.wins = wins;
        this.walls = walls;
        this.q = q;
        this.f = f;
    }

}
