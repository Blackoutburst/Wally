package com.blackoutburst.wally.utils

import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.commands.DisplayPreference
import com.blackoutburst.wally.core.UserOption
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.WallyUser
import java.nio.file.Paths
import java.lang.Exception
import com.blackoutburst.wally.core.Wally
import java.io.FileWriter
import com.google.gson.Gson
import com.blackoutburst.wally.core.logger.LogLevel
import java.io.File
import java.io.FileReader
import java.io.Writer
import java.nio.file.Files

object DatabaseUtils {

    enum class DataType(val dataName: String) {
        HYPIXEL("hypixel"),
        PRACTICE("practice");
    }

    /**
     * Change someone displays preference
     *
     * @param channel the message channel
     * @param discordID the user discord id
     * @param preference the new display preference
     */
    fun updateUserDisplayPreference(channel: MessageChannel, discordID: String, preference: DisplayPreference) {
        val file = "database/linked users/$discordID/data.json"
        val data = getWallyUserData(discordID)

        if (data == null) {
            channel.sendMessage(ConfigManager.loadValue("replies", "not linked or corrupted")).queue()
            return
        }

        data.displayPreference = preference
        generateWallyProfile(data, file)
    }

    /**
     * Get local data if api request fails
     * @param uuid the player uuid
     * @param type the data type
     * @param channel the message channel
     * @return the local data
     */
    fun getBackupLocalData(uuid: String, type: DataType, channel: MessageChannel): String? {
        var data: String? = null
        val typeName = type.dataName

        try {
            val wallyUser = getWallyUserFromUUID(uuid)
            val memberId = wallyUser!!.member.id
            val filePath = Paths.get("database/linked users/$memberId/accounts/$uuid/$typeName.json")

            data = String(Files.readAllBytes(filePath))
            channel.sendMessage(ConfigManager.loadValue("replies", "local stats")).queue()
        } catch (e: Exception) {
            try {
                val filePath = Paths.get("database/leaderboards/$typeName/$uuid/data.json")

                data = String(Files.readAllBytes(filePath))
                channel.sendMessage(ConfigManager.loadValue("replies", "local stats")).queue()
            } catch (e2: Exception) {
                Wally.logger.logStackTrace(e2.stackTrace)
            }
        }
        return data
    }

    /**
     * Save API data to file
     *
     * @param data the player data data
     * @param file the file used to save them
     * @return if the operation worked
     */
    fun generateStats(data: String, file: String): Boolean {
        return try {
            val writer: Writer = FileWriter(file)
            writer.write(data)
            writer.flush()
            writer.close()
            true
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e.stackTrace)
            false
        }
    }

    /**
     * Write wally user data into a JSON file
     * @param wallyProfile the user data
     * @param file the JSON file
     * @return if the operation worked
     */
    fun generateWallyProfile(wallyProfile: UserOption, file: String): Boolean {
        return try {
            val gson = Gson()
            val writer: Writer = FileWriter(file)

            gson.toJson(wallyProfile, writer)
            writer.flush()
            writer.close()
            true
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e.stackTrace)
            false
        }
    }

    /**
     * Update a player stats
     *
     * @param data the api data
     * @param type the stats type
     * @param discordID the user discord id
     * @param uuid the player uuid
     */
    fun updateLinkedPlayerStats(data: String, type: DataType, discordID: String, uuid: String) {
        if (isLinked(discordID)) {
            val typeName = type.dataName
            val rootFolderPath = "database/linked users/$discordID"
            val playerFolderPath = "$rootFolderPath/accounts/$uuid"

            generateStats(data, "$playerFolderPath/$typeName.json")
        }
    }

    /**
     * Update a player stats
     *
     * @param data the api data
     * @param type the stats type
     * @param uuid the player uuid
     */
    fun updateLeaderboardStats(data: String, type: DataType, uuid: String) {
        if (!FilesUtils.isExcluded(uuid)) {
            val typeName = type.dataName
            val rootFolderPath = "database/leaderboards/$typeName"
            val playerFolderPath = "$rootFolderPath/$uuid"
            val playerFolder = File(playerFolderPath)

            if (!playerFolder.exists() && !playerFolder.mkdirs()) {
                Wally.logger.log(LogLevel.WARN, "Player leaderboard folder creation failed")
            }
            generateStats(data, "$playerFolderPath/data.json")
        }
    }

    /**
     * Return wally user data using a discord ID
     *
     * @param discordID the user discord ID
     * @return the Wally user if found
     */
    @JvmStatic
    fun getWallyUserData(discordID: String): UserOption? {
        val gson = Gson()

        return try {
            val reader = FileReader("database/linked users/$discordID/data.json")
            val data = gson.fromJson(reader, UserOption::class.java)

            reader.close()
            data
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e.stackTrace)
            null
        }
    }

    /**
     * Check if a player is linked
     * @param discordID the user discord id
     * @return if the player is linked
     */
    @JvmStatic
    fun isLinked(discordID: String): Boolean {
        val rootFolderPath = "database/linked users/$discordID"

        return File(rootFolderPath).exists()
    }

    /**
     * Check if a player is linked
     * @param uuid the user discord id
     * @return if the player is linked
     */
    @JvmStatic
    fun isLinkedUUID(uuid: String): Boolean {
        return getWallyUserFromUUID(uuid) != null
    }

    /**
     * Check if a player is linked
     * @param uuid the user discord id
     * @return if the player is linked
     */
    fun getWallyUserFromUUID(uuid: String): WallyUser? {
        val wallyUser = Wally.users.stream().filter { user: WallyUser ->
            val memberId = user.member.id
            val folder = File("database/linked users/$memberId/accounts/")
            val entries = folder.list() ?: return@filter false

            listOf(*entries).contains(uuid)
        }.findFirst()
        return wallyUser.orElse(null)
    }
}