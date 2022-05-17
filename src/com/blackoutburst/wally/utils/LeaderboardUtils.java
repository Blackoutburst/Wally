package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.statsobject.StatsObject;

import javax.annotation.Nonnull;
import java.util.List;

public class LeaderboardUtils {

    /**
     * Return a player position inside a leaderboard based on his uuid
     * @param uuid the player uuid
     * @param leaderboard the leaderboard
     * @return the player position inside the leaderboard
     */
    public static int getPlayerPosition(@Nonnull String uuid, List<StatsObject> leaderboard) {
        int position = 0;

        for (StatsObject stats : leaderboard) {
            position++;
            if (uuid.equals(stats.getUUID())) return (position);
        }

        return (0);
    }

    /**
     * Return a player position inside a leaderboard based on his uuid
     * @param uuid the player uuid
     * @param leaderboard the leaderboard
     * @return the player position inside the leaderboard
     */
    public static String getPlayerPositionFormatted(@Nonnull String uuid, List<StatsObject> leaderboard) {
        int position = 0;

        for (StatsObject stats : leaderboard) {
            position++;
            if (uuid.equals(stats.getUUID())) return "(#" + Wally.getInstance().decimalFormat.format(position) + ")";
        }

        return "";
    }

    /**
     * Format the leaderboard position (or any int) to (#x)
     * @param position the leaderboard position
     * @return a formatted version of the leaderboard position
     */
    public static String formatPosition(int position) {
        return "(#" + Wally.getInstance().decimalFormat.format(position) + ")";
    }
}
