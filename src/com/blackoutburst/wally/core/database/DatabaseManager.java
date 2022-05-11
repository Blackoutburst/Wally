package com.blackoutburst.wally.core.database;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;

import javax.annotation.Nonnull;
import java.io.File;

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

        return (DatabaseUtils.generateStats(hypixelData, playerFolderPath + "/hypixel.json") &&
                DatabaseUtils.generateStats(practiceData, playerFolderPath + "/practice.json") &&
                DatabaseUtils.generateWallyProfile(wallyProfile, rootFolderPath + "/data.json"));
    }

    /**
     * Create user data folder
     *
     * @param discordID the user discord id
     * @param hypixelData the user Hypixel player data
     * @return if the operation worked
     */
    public boolean createAltData(@Nonnull String discordID, @Nonnull String playerUUID, @Nonnull String hypixelData, @Nonnull String practiceData) {
        final String rootFolderPath = "database/linked users/" + discordID;
        final String playerFolderPath = rootFolderPath + "/accounts/" + playerUUID;
        final File playerFolder = new File(playerFolderPath);

        if (!playerFolder.mkdirs()) {
            Wally.getInstance().logger.log(LogLevel.WARN, "Couldn't create the directory [" + playerFolderPath + "] aborting");
            return false;
        }

        return (DatabaseUtils.generateStats(hypixelData, playerFolderPath + "/hypixel.json") &&
                DatabaseUtils.generateStats(practiceData, playerFolderPath + "/practice.json"));
    }
}
