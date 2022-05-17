package com.blackoutburst.wally.core.statsobject;

import com.blackoutburst.wally.core.leaderboard.LeaderboardManager;
import com.blackoutburst.wally.utils.APIUtils;
import com.blackoutburst.wally.utils.LeaderboardUtils;

public class HypixelStats implements StatsObject {

    public final String uuid;
    public final String name;
    public final int wins;
    public final int walls;
    public final int qualification;
    public final int finals;
    public final int total;
    public final int totalAPI;
    public final int challenges;
    public final float overall;
    public final int winsPosition;
    public final int wallsPosition;
    public final int qualificationPosition;
    public final int finalsPosition;
    public final int totalPosition;
    public final int totalAPIPosition;
    public final int challengesPosition;

    public HypixelStats(String data) {
        uuid = APIUtils.getValuesFromAPI(data, "player.uuid", "unknown");
        name = APIUtils.getValuesFromAPI(data,"player.displayname", "Unknown");
        wins = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.wins_hole_in_the_wall", "0"));
        walls = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.rounds_hole_in_the_wall", "0"));
        qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.hitw_record_q", "0"));
        finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.hitw_record_f", "0"));
        total = qualification + finals;
        totalAPI = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.achievements.arcade_hitw_practice_makes_perfect", "0"));
        challenges = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.challenges.all_time.ARCADE__hole_in_the_wall_challenge", "0"));

        winsPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().winsLeaderboardHypixel);
        wallsPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().wallLeaderboardHypixel);
        qualificationPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().qualificationLeaderboardHypixel);
        finalsPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().finalsLeaderboardHypixel);
        totalPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().qfLeaderboardHypixel);
        totalAPIPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().qfApiLeaderboardHypixel);
        challengesPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().challengesLeaderboardHypixel);
        overall = (winsPosition + wallsPosition + qualificationPosition + finalsPosition + totalPosition + totalAPIPosition + challengesPosition) / 7.0f;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public int getFinals() {
        return 0;
    }

    @Override
    public int getQualification() {
        return qualification;
    }

    @Override
    public int getTotal() {
        return total;
    }
}
