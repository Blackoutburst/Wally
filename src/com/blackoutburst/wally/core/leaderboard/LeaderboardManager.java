package com.blackoutburst.wally.core.leaderboard;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.leaderboard.comparators.*;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.PracticeStats;
import com.blackoutburst.wally.core.statsobject.StatsObject;
import com.blackoutburst.wally.utils.DatabaseUtils.DataType;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class LeaderboardManager {

    public final List<StatsObject> winsLeaderboardHypixel = new ArrayList<>();
    public final List<StatsObject> wallLeaderboardHypixel = new ArrayList<>();
    public final List<StatsObject> qualificationLeaderboardHypixel = new ArrayList<>();
    public final List<StatsObject> finalsLeaderboardHypixel = new ArrayList<>();
    public final List<StatsObject> qfLeaderboardHypixel = new ArrayList<>();
    public final List<StatsObject> qfApiLeaderboardHypixel = new ArrayList<>();
    public final List<StatsObject> challengesLeaderboardHypixel = new ArrayList<>();
    public final List<StatsObject> overallLeaderboardHypixel = new ArrayList<>();

    public final List<StatsObject> qualificationLeaderboardPractice = new ArrayList<>();
    public final List<StatsObject> finalsLeaderboardPractice = new ArrayList<>();
    public final List<StatsObject> lobbyLeaderboardPractice = new ArrayList<>();
    public final List<StatsObject> wideQualificationLeaderboardPractice = new ArrayList<>();
    public final List<StatsObject> wideFinalsLeaderboardPractice = new ArrayList<>();
    public final List<StatsObject> totalLeaderboardPractice = new ArrayList<>();
    public final List<StatsObject> overallLeaderboardPractice = new ArrayList<>();

    private static LeaderboardManager instance = null;

    /**
     * Populate and sort the overall rank leaderboard
     */
    private void calculateOverallLeaderboards() {
        populate(overallLeaderboardHypixel, DataType.HYPIXEL, false);
        overallLeaderboardHypixel.sort(new OverallComparator());
        populate(overallLeaderboardPractice, DataType.PRACTICE, false);
        overallLeaderboardPractice.sort(new OverallComparator());
    }

    /**
     * Give a leaderboard manager instance
     *
     * @return the leaderboard manager instance
     */
    public static @Nonnull LeaderboardManager getInstance() {
        if (instance == null) {
            Wally.getInstance().logger.log(LogLevel.INFO, "Initializing leaderboard");
            final Instant begin = Instant.now();

            instance = new LeaderboardManager();
            instance.calculateOverallLeaderboards();
            
            Wally.getInstance().logger.log(LogLevel.INFO, "Leaderboard initialized in " + Wally.getInstance().decimalFormat.format((Duration.between(begin, Instant.now()).toMillis() / 1000.0f)) + "s");
            Wally.getInstance().isReady = true;
        }
        return instance;
    }

    /**
     * Populate a leaderboard list using player data from the database
     *
     * @param leaderboard the leaderboard that must be populated
     * @param type the data type
     */
    private void populate(@Nonnull List<StatsObject> leaderboard, DataType type, boolean skipPosition) {
        final File rootFolder = new File("database/leaderboards/" + type.getName());
        final String[] entries = rootFolder.list();
        if (entries == null) return;

        for (String s : entries) {
            final File userFolder = new File(rootFolder.getPath(), s);

            final Path filePath = Paths.get(userFolder.getPath()+"/data.json");
            try {
                final String data = new String(Files.readAllBytes(filePath));

                leaderboard.add(type.equals(DataType.HYPIXEL) ? new HypixelStats(data, skipPosition) : new PracticeStats(data, skipPosition));
            } catch (Exception e) {
                Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            }
        }
    }

    /**
     * Sort a leaderboard using a custom comparator
     *
     * @param leaderboard the leaderboard that must be sorted
     * @param comparator the comparator
     */
    public void sort(@Nonnull List<StatsObject> leaderboard, @Nonnull Comparator<StatsObject> comparator) {
        leaderboard.sort(comparator);
    }

    private LeaderboardManager() {
        populate(winsLeaderboardHypixel, DataType.HYPIXEL, true);
        sort(winsLeaderboardHypixel, new HypixelWinsComparator());
        wallLeaderboardHypixel.addAll(winsLeaderboardHypixel);
        qualificationLeaderboardHypixel.addAll(winsLeaderboardHypixel);
        finalsLeaderboardHypixel.addAll(winsLeaderboardHypixel);
        qfLeaderboardHypixel.addAll(winsLeaderboardHypixel);
        qfApiLeaderboardHypixel.addAll(winsLeaderboardHypixel);
        challengesLeaderboardHypixel.addAll(winsLeaderboardHypixel);

        populate(qualificationLeaderboardPractice, DataType.PRACTICE, true);
        sort(qualificationLeaderboardPractice, new QualificationComparator());
        finalsLeaderboardPractice.addAll(qualificationLeaderboardPractice);
        lobbyLeaderboardPractice.addAll(qualificationLeaderboardPractice);
        wideQualificationLeaderboardPractice.addAll(qualificationLeaderboardPractice);
        wideFinalsLeaderboardPractice.addAll(qualificationLeaderboardPractice);
        totalLeaderboardPractice.addAll(qualificationLeaderboardPractice);

        sort(wallLeaderboardHypixel, new HypixelWallsComparator());
        sort(qualificationLeaderboardHypixel, new QualificationComparator());
        sort(finalsLeaderboardHypixel, new FinalsComparator());
        sort(qfLeaderboardHypixel, new TotalComparator());
        sort(qfApiLeaderboardHypixel,new HypixelTotalAPIComparator());
        sort(challengesLeaderboardHypixel, new HypixelChallengesComparator());

        sort(finalsLeaderboardPractice, new FinalsComparator());
        sort(lobbyLeaderboardPractice, new PracticeLobbyComparator());
        sort(wideQualificationLeaderboardPractice, new PracticeWideQualificationComparator());
        sort(wideFinalsLeaderboardPractice, new PracticeWideFinalsComparator());
        sort(totalLeaderboardPractice, new TotalComparator());
    }
}
