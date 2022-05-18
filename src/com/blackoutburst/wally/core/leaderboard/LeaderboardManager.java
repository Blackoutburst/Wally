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

    public final Leaderboard winsLeaderboardHypixel = new Leaderboard("Hypixel Wins", new HypixelWinsComparator(), Leaderboard.Type.WINS);
    public final Leaderboard wallLeaderboardHypixel = new Leaderboard("Hypixel Walls", new HypixelWallsComparator(), Leaderboard.Type.WALLS);
    public final Leaderboard qualificationLeaderboardHypixel = new Leaderboard("Hypixel Qualification", new QualificationComparator(), Leaderboard.Type.QUALIFICATION);
    public final Leaderboard finalsLeaderboardHypixel = new Leaderboard("Hypixel Finals", new FinalsComparator(), Leaderboard.Type.FINALS);
    public final Leaderboard totalLeaderboardHypixel = new Leaderboard("Hypixel Total Score", new TotalComparator(), Leaderboard.Type.TOTAL);
    public final Leaderboard totalApiLeaderboardHypixel = new Leaderboard("Hypixel Total Score (API)", new HypixelTotalAPIComparator(), Leaderboard.Type.TOTAL_API);
    public final Leaderboard challengesLeaderboardHypixel = new Leaderboard("Hypixel Daily Challenges", new HypixelChallengesComparator(), Leaderboard.Type.CHALLENGES);
    public final Leaderboard overallLeaderboardHypixel = new Leaderboard("Hypixel Overall rank", new OverallComparator(), Leaderboard.Type.OVERALL);

    public final Leaderboard qualificationLeaderboardPractice = new Leaderboard("Practice Qualification", new QualificationComparator(), Leaderboard.Type.QUALIFICATION);
    public final Leaderboard finalsLeaderboardPractice = new Leaderboard("Practice Finals", new FinalsComparator(), Leaderboard.Type.FINALS);
    public final Leaderboard lobbyLeaderboardPractice = new Leaderboard("Practice Lobby", new PracticeLobbyComparator(), Leaderboard.Type.LOBBY);
    public final Leaderboard wideQualificationLeaderboardPractice = new Leaderboard("Practice Wide Qualification", new PracticeWideQualificationComparator(), Leaderboard.Type.WIDE_QUALIFICATION);
    public final Leaderboard wideFinalsLeaderboardPractice = new Leaderboard("Practice Wide Finals", new PracticeWideFinalsComparator(), Leaderboard.Type.WIDE_FINALS);
    public final Leaderboard totalLeaderboardPractice = new Leaderboard("Practice Total Score", new TotalComparator(), Leaderboard.Type.TOTAL);
    public final Leaderboard overallLeaderboardPractice = new Leaderboard("Practice Overall rank", new OverallComparator(), Leaderboard.Type.OVERALL);

    private static LeaderboardManager instance = null;

    /**
     * Populate and sort the overall rank leaderboard
     * They must be processed after everything is set
     * because they need every other leaderboard to actually calculate the overall
     */
    private void calculateOverallLeaderboards() {
        populate(overallLeaderboardHypixel.players, DataType.HYPIXEL, false);
        sort(overallLeaderboardHypixel);
        populate(overallLeaderboardPractice.players, DataType.PRACTICE, false);
        sort(overallLeaderboardPractice);
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
     */
    public void sort(@Nonnull Leaderboard leaderboard) {
        leaderboard.players.sort(leaderboard.comparator);
    }

    private LeaderboardManager() {
        populate(winsLeaderboardHypixel.players, DataType.HYPIXEL, true);
        sort(winsLeaderboardHypixel);
        wallLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players);
        qualificationLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players);
        finalsLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players);
        totalLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players);
        totalApiLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players);
        challengesLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players);

        populate(qualificationLeaderboardPractice.players, DataType.PRACTICE, true);
        sort(qualificationLeaderboardPractice);
        finalsLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players);
        lobbyLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players);
        wideQualificationLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players);
        wideFinalsLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players);
        totalLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players);

        sort(wallLeaderboardHypixel);
        sort(qualificationLeaderboardHypixel);
        sort(finalsLeaderboardHypixel);
        sort(totalLeaderboardHypixel);
        sort(totalApiLeaderboardHypixel);
        sort(challengesLeaderboardHypixel);

        sort(finalsLeaderboardPractice);
        sort(lobbyLeaderboardPractice);
        sort(wideQualificationLeaderboardPractice);
        sort(wideFinalsLeaderboardPractice);
        sort(totalLeaderboardPractice);
    }
}
