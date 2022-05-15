package com.blackoutburst.wally.core.leaderboard;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.PracticeStats;
import com.blackoutburst.wally.core.statsobject.StatObject;
import com.blackoutburst.wally.utils.APIUtils;
import com.blackoutburst.wally.utils.DatabaseUtils.DataType;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class LeaderboardManager {

    public final Set<StatObject> winsLeaderboardHypixel = new HashSet<>();
    public final Set<StatObject> wallLeaderboardHypixel = new HashSet<>();
    public final Set<StatObject> qualificationLeaderboardHypixel = new HashSet<>();
    public final Set<StatObject> finalsLeaderboardHypixel = new HashSet<>();
    public final Set<StatObject> qfLeaderboardHypixel = new HashSet<>();
    public final Set<StatObject> qfApiLeaderboardHypixel = new HashSet<>();
    public final Set<StatObject> challengesLeaderboardHypixel = new HashSet<>();

    public final Set<StatObject> qualificationLeaderboardPractice = new HashSet<>();
    public final Set<StatObject> finalsLeaderboardPractice = new HashSet<>();
    public final Set<StatObject> lobbyLeaderboardPractice = new HashSet<>();
    public final Set<StatObject> wideQualificationLeaderboardPractice = new HashSet<>();
    public final Set<StatObject> wideFinalsLeaderboardPractice = new HashSet<>();
    public final Set<StatObject> totalLeaderboardPractice = new HashSet<>();

    private static LeaderboardManager instance = null;

    /**
     * Give a leaderboard manager instance
     * 
     * @return the leaderboard manager instance
     */
    public static @Nonnull LeaderboardManager getInstance() {
        if (instance == null)
            instance = new LeaderboardManager();
        return instance;
    }

    /**
     * Populate a leaderboard set using player data from the database
     *
     * @param leaderboard the leaderboard that must be populated
     * @param type the data type
     */
    private void populate(@Nonnull Set<StatObject> leaderboard, DataType type) {
        final File rootFolder = new File("/database/leaderboards/" + type.getName());
        final String[] entries = rootFolder.list();
        if (entries == null) return;

        for (String s : entries) {
            final File userFolder = new File(rootFolder.getPath(), s);

            final Path filePath = Paths.get(userFolder.getPath()+"/data.json");
            try {
                final String data = new String(Files.readAllBytes(filePath));

                leaderboard.add(type.equals(DataType.HYPIXEL) ? new HypixelStats(data) : new PracticeStats(data));
            } catch (Exception e) {
                Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            }
        }
    }

    private LeaderboardManager() {
        Wally.getInstance().logger.log(LogLevel.INFO, "Initializing leaderboard");
        long time = System.currentTimeMillis();
        populate(winsLeaderboardHypixel, DataType.HYPIXEL);
        populate(wallLeaderboardHypixel, DataType.HYPIXEL);
        populate(qualificationLeaderboardHypixel, DataType.HYPIXEL);
        populate(finalsLeaderboardHypixel, DataType.HYPIXEL);
        populate(qfLeaderboardHypixel, DataType.HYPIXEL);
        populate(qfApiLeaderboardHypixel, DataType.HYPIXEL);
        populate(challengesLeaderboardHypixel, DataType.HYPIXEL);
        populate(qualificationLeaderboardPractice, DataType.PRACTICE);
        populate(finalsLeaderboardPractice, DataType.PRACTICE);
        populate(lobbyLeaderboardPractice, DataType.PRACTICE);
        populate(wideQualificationLeaderboardPractice, DataType.PRACTICE);
        populate(wideFinalsLeaderboardPractice, DataType.PRACTICE);
        populate(totalLeaderboardPractice, DataType.PRACTICE);

        Wally.getInstance().logger.log(LogLevel.INFO, "Leaderboard initialized in " + (System.currentTimeMillis() - time) + "ms");
    }
}
