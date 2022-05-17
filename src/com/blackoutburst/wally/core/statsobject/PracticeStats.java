package com.blackoutburst.wally.core.statsobject;

import com.blackoutburst.wally.core.leaderboard.LeaderboardManager;
import com.blackoutburst.wally.utils.APIUtils;
import com.blackoutburst.wally.utils.LeaderboardUtils;

public class PracticeStats implements StatsObject {

    public final String uuid;
    public final String name;
    public final int qualification;
    public final int finals;
    public final int lobby;
    public final int wideQualification;
    public final int wideFinals;
    public final int total;
    public final float overall;
    public final int qualificationPosition;
    public final int finalsPosition;
    public final int lobbyPosition;
    public final int wideQualificationPosition;
    public final int wideFinalsPosition;
    public final int totalPosition;

    public PracticeStats(String data) {
        uuid = APIUtils.getValuesFromAPI(data, "uuid", "unknown");
        name = APIUtils.getValuesFromAPI(data,"name", "Unknown");
        qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.qualification", "0"));
        finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.finals", "0"));
        lobby = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.lobby", "0"));
        wideQualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_qualification", "0"));
        wideFinals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_finals", "0"));
        total = qualification + finals + lobby + wideQualification + wideFinals;

        qualificationPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().qualificationLeaderboardPractice);
        finalsPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().finalsLeaderboardPractice);
        lobbyPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().lobbyLeaderboardPractice);
        wideQualificationPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().wideQualificationLeaderboardPractice);
        wideFinalsPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().wideFinalsLeaderboardPractice);
        totalPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().totalLeaderboardPractice);
        overall = (qualificationPosition + finalsPosition + wideQualificationPosition + wideFinalsPosition + totalPosition) / 5.0f;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public int getFinals() {
        return finals;
    }

    @Override
    public int getQualification() {
        return qualification;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public float getOverall() {
        return overall;
    }
}
