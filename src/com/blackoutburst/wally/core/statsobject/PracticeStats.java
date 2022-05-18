package com.blackoutburst.wally.core.statsobject;

import com.blackoutburst.wally.core.leaderboard.Leaderboard;
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager;
import com.blackoutburst.wally.utils.APIUtils;
import com.blackoutburst.wally.utils.LeaderboardUtils;
import org.jetbrains.annotations.NotNull;

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

        if (noLeaderboard.length > 0 && noLeaderboard[0]) {
            qualificationPosition = 0;
            finalsPosition = 0;
            lobbyPosition = 0;
            wideQualificationPosition = 0;
            wideFinalsPosition = 0;
            totalPosition = 0;
            overall = 0;
            overallPosition = 0;
        } else {
            qualificationPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().qualificationLeaderboardPractice);
            finalsPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().finalsLeaderboardPractice);
            lobbyPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().lobbyLeaderboardPractice);
            wideQualificationPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().wideQualificationLeaderboardPractice);
            wideFinalsPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().wideFinalsLeaderboardPractice);
            totalPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().totalLeaderboardPractice);
            overall = (qualificationPosition + finalsPosition + wideQualificationPosition + wideFinalsPosition + totalPosition) / 5.0f;
            overallPosition = LeaderboardUtils.getPlayerPosition(uuid, LeaderboardManager.getInstance().overallLeaderboardPractice);
        }
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getUUID() {
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

    @Override
    public int getDataFromType(Leaderboard.Type type) {
        switch (type) {
            case QUALIFICATION: return qualification;
            case FINALS: return finals;
            case LOBBY: return lobby;
            case WIDE_QUALIFICATION: return wideQualification;
            case WIDE_FINALS: return wideFinals;
            case TOTAL: return total;
            default: return 0;
        }
    }
}
