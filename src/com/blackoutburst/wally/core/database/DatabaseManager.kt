package com.blackoutburst.wally.core.database

import com.blackoutburst.wally.utils.DatabaseUtils.generateStats
import com.blackoutburst.wally.utils.DatabaseUtils.generateWallyProfile
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.UserOption
import java.io.File

object DatabaseManager {

    /**
     * Create root folder for the database
     *
     * @param folderPath the path of the folder
     */
    private fun createRootFolders(folderPath: String) {
        val rootFolder = File(folderPath)
        if (!rootFolder.exists()) {
            if (rootFolder.mkdir()) {
                Wally.logger.log(LogLevel.INFO, "$folderPath created")
            } else {
                Wally.logger.log(LogLevel.WARN, "couldn't create $folderPath")
            }
        }
    }

    /**
     * Initialize default parameters for the database
     */
    init {
        createRootFolders("database")
        createRootFolders("database/leaderboards")
        createRootFolders("database/leaderboards/hypixel")
        createRootFolders("database/leaderboards/practice")
        createRootFolders("database/linked users")
    }

    /**
     * Delete a directory with all his content
     *
     * @param rootFolder the folder you wish to clean
     * @return if the operation worked
     */
    fun cleanFolder(rootFolder: File): Boolean {
        val contents = rootFolder.listFiles()
        if (contents != null) {
            for (file in contents) {
                cleanFolder(file)
            }
        }
        return rootFolder.delete()
    }

    /**
     * Create user data folder
     *
     * @param discordID the user discord id
     * @param hypixelData the user Hypixel player data
     * @param wallyProfile the wally user data
     * @return if the operation worked
     */
    fun createUserData(discordID: String, playerUUID: String, hypixelData: String, practiceData: String, wallyProfile: UserOption): Boolean {
        val rootFolderPath = "database/linked users/$discordID"
        val playerFolderPath = "$rootFolderPath/accounts/$playerUUID"
        val rootFolder = File(rootFolderPath)
        val playerFolder = File(playerFolderPath)
        val resFolder = File("$rootFolderPath/resources")

        if (rootFolder.exists()) {
            Wally.logger.log(LogLevel.INFO, "The directory [$rootFolderPath] already exist")

            if (!cleanFolder(rootFolder)) {
                Wally.logger.log(
                    LogLevel.WARN,
                    "Couldn't delete the directory [$rootFolderPath] aborting"
                )
                return false
            }
        }

        if (!rootFolder.mkdir()) {
            Wally.logger.log(LogLevel.WARN, "Couldn't create the directory [$rootFolderPath] aborting")
            return false
        }

        if (!resFolder.mkdir()) {
            Wally.logger.log(LogLevel.WARN,"Couldn't create the directory [$rootFolderPath/resources] aborting")
            return false
        }

        return if (!playerFolder.mkdirs()) {
            Wally.logger.log(LogLevel.WARN, "Couldn't create the directory [$playerFolderPath] aborting")
            false
        } else {
            generateStats(hypixelData, "$playerFolderPath/hypixel.json") &&
            generateStats(practiceData, "$playerFolderPath/practice.json") &&
            generateWallyProfile(wallyProfile, "$rootFolderPath/data.json")
        }
    }

    /**
     * Create user data folder
     *
     * @param discordID the user discord id
     * @param hypixelData the user Hypixel player data
     * @return if the operation worked
     */
    fun createAltData(discordID: String, playerUUID: String, hypixelData: String, practiceData: String): Boolean {
        val rootFolderPath = "database/linked users/$discordID"
        val playerFolderPath = "$rootFolderPath/accounts/$playerUUID"
        val playerFolder = File(playerFolderPath)

        return if (!playerFolder.mkdirs()) {
            Wally.logger.log(LogLevel.WARN, "Couldn't create the directory [$playerFolderPath] aborting")
            false
        } else {
            generateStats(hypixelData, "$playerFolderPath/hypixel.json") &&
            generateStats(practiceData, "$playerFolderPath/practice.json")
        }
    }
}