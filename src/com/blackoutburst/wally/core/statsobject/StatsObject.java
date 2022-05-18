package com.blackoutburst.wally.core.statsobject;

import com.blackoutburst.wally.core.leaderboard.Leaderboard;

import javax.annotation.Nonnull;

public interface StatsObject {

    @Nonnull String getName();

    @Nonnull String getUUID();

    int getFinals();

    int getQualification();

    int getTotal();

    float getOverall();

    int getDataFromType(Leaderboard.Type type);
}
