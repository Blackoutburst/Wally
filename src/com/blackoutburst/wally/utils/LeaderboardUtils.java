package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.leaderboard.Leaderboard;
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager;
import com.blackoutburst.wally.core.statsobject.StatsObject;

import javax.annotation.Nonnull;

public class LeaderboardUtils {

    /**
     * Get a leaderboard from index
     * @param index the index
     * @return the leaderboard
     */
    public static @Nonnull Leaderboard getLeaderboardFromIndex(int index) {
        switch (index) {
            case 1: return LeaderboardManager.getInstance().wallLeaderboardHypixel;
            case 2: return LeaderboardManager.getInstance().qualificationLeaderboardHypixel;
            case 3: return LeaderboardManager.getInstance().finalsLeaderboardHypixel;
            case 4: return LeaderboardManager.getInstance().totalLeaderboardHypixel;
            case 5: return LeaderboardManager.getInstance().totalApiLeaderboardHypixel;
            case 6: return LeaderboardManager.getInstance().challengesLeaderboardHypixel;
            case 7: return LeaderboardManager.getInstance().overallLeaderboardHypixel;
            case 8: return LeaderboardManager.getInstance().qualificationLeaderboardPractice;
            case 9: return LeaderboardManager.getInstance().finalsLeaderboardPractice;
            case 10: return LeaderboardManager.getInstance().lobbyLeaderboardPractice;
            case 11: return LeaderboardManager.getInstance().wideQualificationLeaderboardPractice;
            case 12: return LeaderboardManager.getInstance().wideFinalsLeaderboardPractice;
            case 13: return LeaderboardManager.getInstance().totalLeaderboardPractice;
            case 14: return LeaderboardManager.getInstance().overallLeaderboardPractice;
            default: return LeaderboardManager.getInstance().winsLeaderboardHypixel;
        }
    }

    /**
     * Return a player position inside a leaderboard based on his uuid
     * @param uuid the player uuid
     * @param leaderboard the leaderboard
     * @return the player position inside the leaderboard
     */
    public static int getPlayerPosition(@Nonnull String uuid, Leaderboard leaderboard) {
        int position = 0;

        for (StatsObject stats : leaderboard.players) {
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
    public static String getPlayerPositionFormatted(@Nonnull String uuid, Leaderboard leaderboard) {
        int position = 0;

        for (StatsObject stats : leaderboard.players) {
            position++;
            if (uuid.equals(stats.getUUID())) return "(#" + Wally.getInstance().numberFormat.format(position) + ")";
        }

        return "";
    }

    /**
     * Format the leaderboard position (or any int) to (#x)
     * return nothing is the position is 0 (not on leaderboard)
     * @param position the leaderboard position
     * @return a formatted version of the leaderboard position
     */
    public static String formatPosition(int position) {
        return position == 0 ? "" : "(#" + Wally.getInstance().numberFormat.format(position) + ")";
    }
}
