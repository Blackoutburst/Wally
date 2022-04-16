package com.blackoutburst.wally.core.database;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Utils;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.stats.HypixelStats;
import com.blackoutburst.wally.core.stats.PracticeStats;
import com.google.gson.Gson;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DatabaseManager {

    private static DatabaseManager instance = null;

    /**
     * Get the database manager instance
     *
     * @return the instance
     */
    public static DatabaseManager getInstance() {
        if (instance == null)
            instance = new DatabaseManager();

        return (instance);
    }

    /**
     * Create root folder for the database
     *
     * @param folderPath the path of the folder
     */
     private void createRootFolders(String folderPath) {
        final File rootFolder = new File(folderPath);
        if (!rootFolder.exists()) {
            if (rootFolder.mkdir()) {
                Wally.getInstance().logger.log(LogLevel.INFO, folderPath+" created");
            } else {
                Wally.getInstance().logger.log(LogLevel.WARN, "couldn't create "+folderPath);
            }
        }
    }

    /**
     * Initialize default parameters for the database
     */
    private DatabaseManager() {
        createRootFolders("database");
        createRootFolders("database/leaderboards");
        createRootFolders("database/leaderboards/hypixel");
        createRootFolders("database/leaderboards/practice");
        createRootFolders("database/linked users");
    }

    /**
     * Delete a directory with all his content
     *
     * @param rootFolder the folder you wish to clean
     * @return if the operation worked
     */
    public boolean cleanFolder(@Nonnull File rootFolder) {
        final File[] contents = rootFolder.listFiles();

        if (contents != null) {
            for (final File file : contents) {
                cleanFolder(file);
            }
        }

        return (rootFolder.delete());
    }

    /**
     * Save Hypixel data to file
     *
     * @param hypixelData the player Hypixel data
     * @param file the file used to save them
     * @return if the operation worked
     */
    private boolean generateHypixelStats(@Nonnull String hypixelData, @Nonnull String file) {
        try {
            final Gson gson = new Gson();

            final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            final DecimalFormat df = (DecimalFormat) nf;

            final String wins = df.format(Integer.parseInt(Utils.getValuesFromAPI(hypixelData, "player.stats.Arcade.wins_hole_in_the_wall", "0")));
            final String walls = df.format(Integer.parseInt(Utils.getValuesFromAPI(hypixelData, "player.stats.Arcade.rounds_hole_in_the_wall", "0")));
            final String q = df.format(Integer.parseInt(Utils.getValuesFromAPI(hypixelData, "player.stats.Arcade.hitw_record_q", "0")));
            final String f = df.format(Integer.parseInt(Utils.getValuesFromAPI(hypixelData, "player.stats.Arcade.hitw_record_f", "0")));

            final HypixelStats stats = new HypixelStats(wins, walls, q, f);

            final Writer writer = new FileWriter(file);

            gson.toJson(stats, writer);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.ERROR, e.getMessage());
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
    private boolean generatePracticeStats(@Nonnull String practiceData, @Nonnull String file) {
        try {
            final Gson gson = new Gson();

            final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            final DecimalFormat df = (DecimalFormat) nf;

            final String q = df.format(Integer.parseInt(Utils.getValuesFromAPI(practiceData, "scores.qualification", "0")));
            final String f = df.format(Integer.parseInt(Utils.getValuesFromAPI(practiceData, "scores.finals", "0")));
            final String l = df.format(Integer.parseInt(Utils.getValuesFromAPI(practiceData, "scores.lobby", "0")));
            final String wq = df.format(Integer.parseInt(Utils.getValuesFromAPI(practiceData, "scores.wide_qualification", "0")));
            final String wf = df.format(Integer.parseInt(Utils.getValuesFromAPI(practiceData, "scores.wide_finals", "0")));

            final PracticeStats stats = new PracticeStats(q, f, l, wq, wf);

            final Writer writer = new FileWriter(file);

            gson.toJson(stats, writer);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.ERROR, e.getMessage());
            return false;
        }
    }

    /**
     * Write wally user data into a JSON file
     * @param wallyProfile the user data
     * @param file the JSON file
     * @return if the operation worked
     */
    private boolean generateWallyProfile(@Nonnull UserOption wallyProfile, @Nonnull String file) {
        try {
            final Gson gson = new Gson();

            final Writer writer = new FileWriter(file);

            gson.toJson(wallyProfile, writer);
            writer.flush();
            writer.close();

            return true;
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.ERROR, e.getMessage());
            return false;
        }
    }

    /**
     * Create user data folder
     *
     * @param discordID the user discord id
     * @param hypixelData the user Hypixel player data
     * @param wallyProfile the wally user data
     * @return if the operation worked
     */
    public boolean createUserData(@Nonnull String discordID, @Nonnull String playerUUID, @Nonnull String hypixelData, @Nonnull String practiceData, @Nonnull UserOption wallyProfile) {
        final String rootFolderPath = "database/linked users/" + discordID;
        final String playerFolderPath = rootFolderPath + "/accounts/" + playerUUID;
        final File rootFolder = new File(rootFolderPath);
        final File playerFolder = new File(playerFolderPath);
        final File resFolder = new File(rootFolderPath + "/resources");

        if (rootFolder.exists()) {
            Wally.getInstance().logger.log(LogLevel.INFO, "The directory [" + rootFolderPath + "] already exist");
            if (!cleanFolder(rootFolder)) {
                Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't delete the directory [" + rootFolderPath + "] aborting");
                return false;
            }
        }

        if (!rootFolder.mkdir()) {
            Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't create the directory [" + rootFolderPath + "] aborting");
            return false;
        }

        if (!resFolder.mkdir()) {
            Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't create the directory [" + rootFolderPath + "/resources" + "] aborting");
            return false;
        }

        if (!playerFolder.mkdirs()) {
            Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't create the directory [" + playerFolderPath + "] aborting");
            return false;
        }

        return (generateHypixelStats(hypixelData, playerFolderPath + "/hypixel.json") &&
                generatePracticeStats(practiceData, playerFolderPath + "/practice.json") &&
                generateWallyProfile(wallyProfile, rootFolderPath + "/data.json"));
    }

}
