package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.statsobject.StatsObject;

import javax.annotation.Nonnull;
import java.util.Set;

public class LeaderboardUtils {

    /**
     * Return a player position inside a leaderboard based on his uuid
     * @param uuid the player uuid
     * @param leaderboard the leaderboard
     * @return the player position inside the leaderboard
     */
    public static int getPlayerPosition(@Nonnull String uuid, Set<StatsObject> leaderboard) {
        int position = 1;

        for (StatsObject stats : leaderboard) {
            if (uuid.equals(stats.getUUID())) break;
            position++;
        }

        return (position);
    }
}
