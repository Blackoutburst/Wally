package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.stats.HypixelStats;
import com.blackoutburst.wally.core.stats.PracticeStats;
import com.google.gson.Gson;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DatabaseUtils {

    /**
     * Save Hypixel data to file
     *
     * @param hypixelData the player Hypixel data
     * @param file the file used to save them
     * @return if the operation worked
     */
    public static boolean generateHypixelStats(@Nonnull String hypixelData, @Nonnull String file) {
        try {
            final Gson gson = new Gson();

            final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            final DecimalFormat df = (DecimalFormat) nf;

            final String wins = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(hypixelData, "player.stats.Arcade.wins_hole_in_the_wall", "0")));
            final String walls = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(hypixelData, "player.stats.Arcade.rounds_hole_in_the_wall", "0")));
            final String q = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(hypixelData, "player.stats.Arcade.hitw_record_q", "0")));
            final String f = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(hypixelData, "player.stats.Arcade.hitw_record_f", "0")));

            final HypixelStats stats = new HypixelStats(wins, walls, q, f);

            final Writer writer = new FileWriter(file);

            gson.toJson(stats, writer);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return false;
        }
    }

    /**
     * Save practice data to file
     *
     * @param practiceData the player Practice data
     * @param file the file used to save them
     * @return if the operation worked
     */
    public static boolean generatePracticeStats(@Nonnull String practiceData, @Nonnull String file) {
        try {
            final Gson gson = new Gson();

            final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            final DecimalFormat df = (DecimalFormat) nf;

            final String q = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(practiceData, "scores.qualification", "0")));
            final String f = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(practiceData, "scores.finals", "0")));
            final String l = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(practiceData, "scores.lobby", "0")));
            final String wq = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(practiceData, "scores.wide_qualification", "0")));
            final String wf = df.format(Integer.parseInt(APIUtils.getValuesFromAPI(practiceData, "scores.wide_finals", "0")));

            final PracticeStats stats = new PracticeStats(q, f, l, wq, wf);

            final Writer writer = new FileWriter(file);

            gson.toJson(stats, writer);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return false;
        }
    }

    /**
     * Write wally user data into a JSON file
     * @param wallyProfile the user data
     * @param file the JSON file
     * @return if the operation worked
     */
    public static boolean generateWallyProfile(@Nonnull UserOption wallyProfile, @Nonnull String file) {
        try {
            final Gson gson = new Gson();

            final Writer writer = new FileWriter(file);

            gson.toJson(wallyProfile, writer);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return false;
        }
    }

    /**
     * Return wally user data using a discord ID
     * @param discordID
     * @return
     */
    public static UserOption getWallyUserData(@Nonnull String discordID) {
        final Gson gson = new Gson();

        try {
            final FileReader reader = new FileReader("database/linked users/" + discordID + "/data.json");
            final UserOption data = gson.fromJson(reader, UserOption.class);

            reader.close();

            return (data);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return (null);
        }
    }

    /**
     * Check if a player is linked
     * @param discordID the user discord id
     * @return if the player is linked
     */
    public static boolean isLinked(@Nonnull String discordID) {
        final String rootFolderPath = "database/linked users/" + discordID;
        return (new File(rootFolderPath).exists());
    }

    /**
     * Check if a player is linked
     * @param uuid the user discord id
     * @return if the player is linked
     */
    public static boolean isLinkedUUID(@Nonnull String uuid) {
        boolean linked = false;

        for (WallyUser w : Wally.getInstance().users) {
            if (w.getOptions().getMainAccountUUID().equals(uuid)) {
                linked = true;
                break;
            }
        }

        return linked;
    }

    /**
     * Check if a player is linked
     * @param uuid the user discord id
     * @return if the player is linked
     */
    public static WallyUser getWallyUserFromUUID(@Nonnull String uuid) {
        for (WallyUser w : Wally.getInstance().users) {
            if (w.getOptions().getMainAccountUUID().equals(uuid)) {
                return (w);
            }
        }
        return null;
    }
}
