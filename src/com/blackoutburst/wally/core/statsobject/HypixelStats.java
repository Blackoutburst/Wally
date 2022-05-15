package com.blackoutburst.wally.core.statsobject;

import com.blackoutburst.wally.utils.APIUtils;

public class HypixelStats {

    public final String uuid;
    public final String name;
    public final int wins;
    public final int walls;
    public final int qualification;
    public final int finals;
    public final int QF;
    public final int QFAPI;
    public final int challenges;

    public HypixelStats(String data) {
        uuid = APIUtils.getValuesFromAPI(data, "player.uuid", "unknown");
        name = APIUtils.getValuesFromAPI(data,"player.displayname", "Unknown");
        wins = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.wins_hole_in_the_wall", "0"));
        walls = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.rounds_hole_in_the_wall", "0"));
        qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.hitw_record_q", "0"));
        finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.hitw_record_f", "0"));
        QF = qualification + finals;
        QFAPI = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.achievements.arcade_hitw_practice_makes_perfect", "0"));
        challenges = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.challenges.all_time.ARCADE__hole_in_the_wall_challenge", "0"));
    }

}
