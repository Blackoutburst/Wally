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
    public final int overallPosition;

    public PracticeStats(String data, boolean... noLeaderboard) {
        uuid = APIUtils.getValuesFromAPI(data, "uuid", "unknown");
        name = APIUtils.getValuesFromAPI(data,"name", "Unknown");
        qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.qualification", "0"));
        finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.finals", "0"));
        lobby = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.lobby", "0"));
        wideQualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_qualification", "0"));
        wideFinals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_finals", "0"));
        total = qualification + finals + lobby + wideQualification + wideFinals;

        qualificationPosition = noLeaderboard.length > 0 && noLeaderboard[0] ? 0 : LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().qualificationLeaderboardPractice);
        finalsPosition = noLeaderboard.length > 0 && noLeaderboard[0] ? 0 : LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().finalsLeaderboardPractice);
        lobbyPosition = noLeaderboard.length > 0 && noLeaderboard[0] ? 0 : LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().lobbyLeaderboardPractice);
        wideQualificationPosition = noLeaderboard.length > 0 && noLeaderboard[0] ? 0 : LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().wideQualificationLeaderboardPractice);
        wideFinalsPosition = noLeaderboard.length > 0 && noLeaderboard[0] ? 0 : LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().wideFinalsLeaderboardPractice);
        totalPosition = noLeaderboard.length > 0 && noLeaderboard[0] ? 0 : LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().totalLeaderboardPractice);

        overall = noLeaderboard.length > 0 && noLeaderboard[0] ? 0 : (qualificationPosition + finalsPosition + wideQualificationPosition + wideFinalsPosition + totalPosition) / 5.0f;

        overallPosition = noLeaderboard.length > 0 && noLeaderboard[0] ? 0 : LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().overallLeaderboardPractice);
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
