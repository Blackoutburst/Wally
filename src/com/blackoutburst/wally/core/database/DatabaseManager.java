package com.blackoutburst.wally.core.database;

import com.blackoutburst.wally.core.Utils;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.stats.HypixelStats;
import com.google.gson.Gson;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
    private boolean cleanFolder(@Nonnull File rootFolder) {
        final File[] contents = rootFolder.listFiles();

        if (contents != null) {
            for (final File file : contents) {
                cleanFolder(file);
            }
        }

        return (rootFolder.delete());
    }

    private void generateHypixelStats(@Nonnull String playerData, @Nonnull String file) {
        final Gson gson = new Gson();

        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        final DecimalFormat df = (DecimalFormat) nf;

        final String wins = df.format(Utils.getValuesFromAPI(playerData, "player.stats.Arcade.wins_hole_in_the_wall"));
        final String walls = df.format(Utils.getValuesFromAPI(playerData, "player.stats.Arcade.rounds_hole_in_the_wall"));
        final String q = df.format(Utils.getValuesFromAPI(playerData, "player.stats.Arcade.hitw_record_q"));
        final String f = df.format(Utils.getValuesFromAPI(playerData, "player.stats.Arcade.hitw_record_f"));

        final HypixelStats stats = new HypixelStats(wins, walls, q, f);

        try {
            gson.toJson(stats, new FileWriter(file));
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.ERROR, e.getMessage());
        }
    }

    /**
     * Create user data folder
     *
     * @param discordID the user discord id
     * @param playerData the user Hypixel player data
     * @return if the operation worked
     */
    public boolean createUserData(@Nonnull String discordID, @Nonnull String playerData) {
        final String folderPath = "database/linked users/" + discordID;
        final File rootFolder = new File(folderPath);

        if (rootFolder.exists()) {
            Wally.getInstance().logger.log(LogLevel.INFO, "The directory [" + folderPath + "] already exist");
            if (!cleanFolder(rootFolder)) {
                Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't delete the directory [" + folderPath + "] aborting");
                return false;
            }
        }

        if (!rootFolder.mkdir()) {
            Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't create the directory [" + folderPath + "] aborting");
            return false;
        }

        generateHypixelStats(playerData, folderPath + "/hypixel.json");

        return true;
    }

}
