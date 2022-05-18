package com.blackoutburst.wally.core.statsobject;

import com.blackoutburst.wally.core.leaderboard.Leaderboard;

public interface StatsObject {

    String getUUID();

    int getFinals();

    int getQualification();

    int getTotal();

    float getOverall();

    int getDataFromType(Leaderboard.Type type);
}
