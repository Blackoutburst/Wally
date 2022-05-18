package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.leaderboard.Leaderboard;
import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.PracticeStats;
import com.blackoutburst.wally.core.statsobject.StatsObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

public class EmbedUtils {

    /**
     * Create an embed with leaderboard information inside
     * @param leaderboard the leaderboard
     * @param page the leaderboard page
     * @return an embed containing every hole in the wall stats
     */
    public static MessageEmbed generateLeaderboardEmbed(@Nonnull Leaderboard leaderboard, int page) {
        int leaderboardPage = (page - 1) * 10;
        if (leaderboard.players.size() <= leaderboardPage) {
            leaderboardPage = leaderboard.players.size() - 10;
        }
        StatsObject topPlayer = leaderboard.players.get(leaderboardPage);

        return new EmbedBuilder()
                .setAuthor(leaderboard.name, "https://hypixel.net/arcade-games/leaderboard/hole_in_the_wall", "https://www.blackoutburst.com/img/wally.png")
                .setColor(Utils.getColorFromScore(Utils.getHighestScore(topPlayer.getScores())))
                .setDescription(MessageUtils.generateLeaderboardMessage(leaderboard, page, false))
                .setFooter(LocalDate.now() + " | " + leaderboard.type)
                .build();
    }

    /**
     * Create an embed with Practice stats information inside
     * @param data a valid Practice json data
     * @return an embed containing every hole in the wall stats
     */
    public static MessageEmbed generatePracticeStatsEmbed(@Nonnull String data) {
        final long time = System.currentTimeMillis();
        final PracticeStats stats = new PracticeStats(data);

        return new EmbedBuilder()
                .setAuthor(stats.name, "https://hypixel.net/player/" + stats.uuid, "https://visage.surgeplay.com/face/512/" + stats.uuid + "?&time=" + time)
                .setThumbnail("https://visage.surgeplay.com/face/512/" + stats.uuid + "?&time=" + time)
                .setTitle("Practice stats", "https://hypixel.net/player/" + stats.uuid)
                .setColor(Utils.getColorFromScore(Utils.getHighestScore(stats.qualification, stats.finals, stats.lobby, stats.wideQualification, stats.wideFinals)))
                .addField("Qualification", Wally.getInstance().numberFormat.format(stats.qualification) + " " + LeaderboardUtils.formatPosition(stats.qualificationPosition), true)
                .addField("Finals", Wally.getInstance().numberFormat.format(stats.finals) + " " + LeaderboardUtils.formatPosition(stats.finalsPosition), true)
                .addBlankField(true)
                .addField("Lobby", Wally.getInstance().numberFormat.format(stats.lobby) + " " + LeaderboardUtils.formatPosition(stats.lobbyPosition), true)
                .addBlankField(true)
                .addBlankField(true)
                .addField("Wide Qualification", Wally.getInstance().numberFormat.format(stats.wideQualification) + " " + LeaderboardUtils.formatPosition(stats.wideQualificationPosition), true)
                .addField("Wide Finals", Wally.getInstance().numberFormat.format(stats.wideFinals) + " " + LeaderboardUtils.formatPosition(stats.wideFinalsPosition), true)
                .addBlankField(true)
                .addField("Total Score", Wally.getInstance().numberFormat.format(stats.total) + " " + LeaderboardUtils.formatPosition(stats.totalPosition), true)
                .addField("Overall Rank", Wally.getInstance().decimalFormat.format(stats.overall) + " " + LeaderboardUtils.formatPosition(stats.overallPosition), true)
                .setFooter(LocalDate.now() + " | " + stats.uuid)
                .build();
    }

    /**
     * Create an embed with Hypixel stats information inside
     * @param data a valid Hypixel json data
     * @return an embed containing every hole in the wall stats
     */
    public static MessageEmbed generateHypixelStatsEmbed(@Nonnull String data) {
        final long time = System.currentTimeMillis();
        final HypixelStats stats = new HypixelStats(data);

        return new EmbedBuilder()
                .setAuthor(stats.name, "https://hypixel.net/player/" + stats.uuid, "https://visage.surgeplay.com/face/512/" + stats.uuid + "?&time=" + time)
                .setThumbnail("https://visage.surgeplay.com/face/512/" + stats.uuid + "?&time=" + time)
                .setTitle("Hypixel stats", "https://hypixel.net/player/" + stats.uuid)
                .setColor(Utils.getColorFromScore(Utils.getHighestScore(stats.qualification, stats.finals)))
                .addField("Wins", Wally.getInstance().numberFormat.format(stats.wins) + " " + LeaderboardUtils.formatPosition(stats.winsPosition), true)
                .addField("Walls", Wally.getInstance().numberFormat.format(stats.walls) + " " + LeaderboardUtils.formatPosition(stats.wallsPosition), true)
                .addBlankField(true)
                .addField("Qualification", Wally.getInstance().numberFormat.format(stats.qualification) + " " + LeaderboardUtils.formatPosition(stats.qualificationPosition), true)
                .addField("Finals", Wally.getInstance().numberFormat.format(stats.finals) + " " + LeaderboardUtils.formatPosition(stats.finalsPosition), true)
                .addBlankField(true)
                .addField("QF", Wally.getInstance().numberFormat.format(stats.total) + " " + LeaderboardUtils.formatPosition(stats.totalPosition), true)
                .addField("QF (according to the API)", Wally.getInstance().numberFormat.format(stats.totalAPI) + " " + LeaderboardUtils.formatPosition(stats.totalAPIPosition), true)
                .addBlankField(true)
                .addField("Challenges", Wally.getInstance().numberFormat.format(stats.challenges) + " " + LeaderboardUtils.formatPosition(stats.challengesPosition), true)
                .addField("Overall Rank", Wally.getInstance().decimalFormat.format(stats.overall) + " " + LeaderboardUtils.formatPosition(stats.overallPosition), true)
                .setFooter(LocalDate.now() + " | " + stats.uuid)
                .build();
    }

    /**
     * Generate the skin preview in embed
     *
     * @param uuid the player uuid
     * @param playerName the player name
     * @return the skin embed
     */
    public static EmbedBuilder generateSkin(@Nonnull String uuid, @Nonnull String playerName) {
        final long time = System.currentTimeMillis();

        return new EmbedBuilder()
                .setAuthor(playerName, "https://fr.namemc.com/profile/"+uuid, "https://visage.surgeplay.com/face/512/" + uuid + "?&time=" + time)
                .setTitle("NameMC", "https://fr.namemc.com/profile/"+uuid)
                .setColor(new Color(71, 165, 255))
                .setImage("https://visage.surgeplay.com/full/512/" + uuid + "?&time=" + time);
    }

    /**
     * Generate the changelog embed message
     * @return the changelog embed
     */
    public static EmbedBuilder generateChangelog() {
        try {
            final String wallyURL = "https://github.com/Blackoutburst/Wally";
            final String wallyPfp = "https://www.blackoutburst.com/img/wally.png";
            final BufferedReader reader = new BufferedReader(new FileReader("config/changelog.txt"));
            final StringBuilder changelog = new StringBuilder();
            final String title = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                changelog.append(line).append("\n");
            }
            reader.close();

            return (new EmbedBuilder()
                    .setAuthor(String.valueOf(LocalDate.now()), wallyURL, wallyPfp)
                    .setTitle(title, wallyURL)
                    .addField("Changes", changelog.toString(), false)
                    .addField("", "[GitHub](https://github.com/Blackoutburst/Wally)", true)
                    .addField("", "[Patreon](https://www.patreon.com/Blackoutburst)", true)
                    .setColor(new Color(71, 165, 255))
                    .setFooter("Made with love by Blackoutburst", "https://www.blackoutburst.com/img/pfp.jpg"));
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        return (null);
    }
}
