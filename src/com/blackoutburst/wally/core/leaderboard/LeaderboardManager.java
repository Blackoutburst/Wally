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
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardManager {

    public final Map<String, StatObject> winsLeaderboardHypixel = new HashMap<>();
    public final Map<String, StatObject> wallLeaderboardHypixel = new HashMap<>();
    public final Map<String, StatObject> qualificationLeaderboardHypixel = new HashMap<>();
    public final Map<String, StatObject> finalsLeaderboardHypixel = new HashMap<>();
    public final Map<String, StatObject> qfLeaderboardHypixel = new HashMap<>();
    public final Map<String, StatObject> qfApiLeaderboardHypixel = new HashMap<>();
    public final Map<String, StatObject> challengesLeaderboardHypixel = new HashMap<>();

    public final Map<String, StatObject> qualificationLeaderboardPractice = new HashMap<>();
    public final Map<String, StatObject> finalsLeaderboardPractice = new HashMap<>();
    public final Map<String, StatObject> lobbyLeaderboardPractice = new HashMap<>();
    public final Map<String, StatObject> wideQualificationLeaderboardPractice = new HashMap<>();
    public final Map<String, StatObject> wideFinalsLeaderboardPractice = new HashMap<>();
    public final Map<String, StatObject> totalLeaderboardPractice = new HashMap<>();

    private static LeaderboardManager instance = null;

    public static @Nonnull LeaderboardManager getInstance() {
        if (instance == null)
            instance = new LeaderboardManager();
        return instance;
    }

    private void populate(@Nonnull Map<String, StatObject> leaderboard, DataType type) {
        final File rootFolder = new File("/database/leaderboards/" + type.getName());
        final String[] entries = rootFolder.list();
        if (entries == null) return;

        for (String s : entries) {
            final File userFolder = new File(rootFolder.getPath(), s);

            final Path filePath = Paths.get(userFolder.getPath()+"/data.json");
            try {
                final String data = new String(Files.readAllBytes(filePath));
                final String uuid = APIUtils.getValuesFromAPI(data, type.equals(DataType.HYPIXEL) ? "player.uuid" : "uuid", "unknown");

                leaderboard.put(uuid, type.equals(DataType.HYPIXEL) ? new HypixelStats(data) : new PracticeStats(data));
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
