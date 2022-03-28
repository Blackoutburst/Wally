package com.blackoutburst.wally.core.database;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;

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

}
