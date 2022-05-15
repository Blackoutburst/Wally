package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.statsobject.HypixelStats;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

public class EmbedUtils {

    /**
     * Create an embed with Practice stats information inside
     * @param data a valid Practice json data
     * @return an embed containing every hole in the wall stats
     */
    public static MessageEmbed generatePracticeStatsEmbed(@Nonnull String data) {
        final long time = System.currentTimeMillis();
        final String uuid = APIUtils.getValuesFromAPI(data, "uuid", "unknown");
        final String name = APIUtils.getValuesFromAPI(data,"name", "Unknown");
        final int qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.qualification", "0"));
        final int finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.finals", "0"));
        final int lobby = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.lobby", "0"));
        final int wideQualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_qualification", "0"));
        final int wideFinals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_finals", "0"));
        final int total = qualification + finals + lobby + wideQualification + wideFinals;

        return new EmbedBuilder()
                .setAuthor(name, "https://hypixel.net/player/"+uuid, "https://visage.surgeplay.com/face/512/" + uuid + "?&time=" + time)
                .setThumbnail("https://visage.surgeplay.com/face/512/" + uuid + "?&time=" + time)
                .setTitle("Practice stats", "https://hypixel.net/player/"+uuid)
                .setColor(Utils.getColorFromScore(Utils.getHighestScore(qualification, finals, lobby, wideQualification, wideFinals)))
                .addField("Qualification", Wally.getInstance().decimalFormat.format(qualification), true)
                .addBlankField(true)
                .addField("Finals", Wally.getInstance().decimalFormat.format(finals), true)
                .addField("Lobby", Wally.getInstance().decimalFormat.format(lobby), true)
                .addBlankField(true)
                .addBlankField(true)
                .addField("Wide Qualification", Wally.getInstance().decimalFormat.format(wideQualification), true)
                .addBlankField(true)
                .addField("Wide Finals", Wally.getInstance().decimalFormat.format(wideFinals), true)
                .addField("Total score", Wally.getInstance().decimalFormat.format(total), true)
                .setFooter(LocalDate.now() + " | " + uuid)
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
                .setAuthor(stats.name, "https://hypixel.net/player/"+stats.uuid, "https://visage.surgeplay.com/face/512/" + stats.uuid + "?&time=" + time)
                .setThumbnail("https://visage.surgeplay.com/face/512/" + stats.uuid + "?&time=" + time)
                .setTitle("Hypixel stats", "https://hypixel.net/player/"+stats.uuid)
                .setColor(Utils.getColorFromScore(Utils.getHighestScore(stats.qualification, stats.finals)))
                .addField("Wins", Wally.getInstance().decimalFormat.format(stats.wins), true)
                .addBlankField(true)
                .addField("Walls", Wally.getInstance().decimalFormat.format(stats.walls), true)
                .addField("Qualification", Wally.getInstance().decimalFormat.format(stats.qualification), true)
                .addBlankField(true)
                .addField("Finals", Wally.getInstance().decimalFormat.format(stats.finals), true)
                .addField("QF", Wally.getInstance().decimalFormat.format(stats.QF), true)
                .addBlankField(true)
                .addField("QF (according to the API)", Wally.getInstance().decimalFormat.format(stats.QFAPI), true)
                .addField("Challenges", Wally.getInstance().decimalFormat.format(stats.challenges), true)
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
            final String wallyPfp = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
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
