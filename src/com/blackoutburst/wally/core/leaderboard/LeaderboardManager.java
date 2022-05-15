package com.blackoutburst.wally.core.leaderboard;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.PracticeStats;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LeaderboardManager {

    public final Map<UUID, HypixelStats> winsLeaderboardHypixel = new HashMap<>();
    public final Map<UUID, HypixelStats> wallLeaderboardHypixel = new HashMap<>();
    public final Map<UUID, HypixelStats> qualificationLeaderboardHypixel = new HashMap<>();
    public final Map<UUID, HypixelStats> finalsLeaderboardHypixel = new HashMap<>();
    public final Map<UUID, HypixelStats> qfLeaderboardHypixel = new HashMap<>();
    public final Map<UUID, HypixelStats> qfApiLeaderboardHypixel = new HashMap<>();
    public final Map<UUID, HypixelStats> challengesLeaderboardHypixel = new HashMap<>();

    public final Map<UUID, PracticeStats> qualificationLeaderboardPractice = new HashMap<>();
    public final Map<UUID, PracticeStats> finalsLeaderboardPractice = new HashMap<>();
    public final Map<UUID, PracticeStats> lobbyLeaderboardPractice = new HashMap<>();
    public final Map<UUID, PracticeStats> wideQualificationLeaderboardPractice = new HashMap<>();
    public final Map<UUID, PracticeStats> wideFinalsLeaderboardPractice = new HashMap<>();
    public final Map<UUID, PracticeStats> totalLeaderboardPractice = new HashMap<>();

    private static LeaderboardManager instance = null;

    public static @Nonnull LeaderboardManager getInstance() {
        if (instance == null)
            instance = new LeaderboardManager();
        return instance;
    }

    private void populate(@Nonnull Map leaderboard) {

    }

    private LeaderboardManager() {
        Wally.getInstance().logger.log(LogLevel.INFO, "Initializing leaderboard");
        long time = System.currentTimeMillis();
        populate(winsLeaderboardHypixel);
        populate(wallLeaderboardHypixel);
        populate(qualificationLeaderboardHypixel);
        populate(finalsLeaderboardHypixel);
        populate(qfLeaderboardHypixel);
        populate(qfApiLeaderboardHypixel);
        populate(challengesLeaderboardHypixel);
        populate(qualificationLeaderboardPractice);
        populate(finalsLeaderboardPractice);
        populate(lobbyLeaderboardPractice);
        populate(wideQualificationLeaderboardPractice);
        populate(wideFinalsLeaderboardPractice);
        populate(totalLeaderboardPractice);

        Wally.getInstance().logger.log(LogLevel.INFO, "Leaderboard initialized in " + (time - System.currentTimeMillis()) + "ms");
    }
}
