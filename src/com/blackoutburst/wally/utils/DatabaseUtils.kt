package com.blackoutburst.wally.utils

import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.commands.DisplayPreference
import com.blackoutburst.wally.core.UserOption
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.WallyUser
import java.nio.file.Paths
import com.blackoutburst.wally.core.Wally
import com.google.gson.Gson
import com.blackoutburst.wally.core.logger.LogLevel
import okio.IOException
import java.io.*
import java.nio.file.Files

object DatabaseUtils {

    enum class DataType(val dataName: String) {
        HYPIXEL("hypixel"),
        PRACTICE("practice");
    }

    fun updateUserDisplayPreference(channel: MessageChannel, discordID: String, preference: DisplayPreference) {
        val file = "database/linked users/$discordID/data.json"
        val data = getWallyUserData(discordID)

        if (data == null) {
            channel.sendMessage(ConfigManager.loadMessage( "not linked or corrupted")).queue()
            return
        }

        data.displayPreference = preference
        generateWallyProfile(data, file)
    }

    fun getBackupLocalData(uuid: String, type: DataType, channel: MessageChannel): String? {
        var data: String? = null
        val typeName = type.dataName

        try {
            val wallyUser = getWallyUserFromUUID(uuid)
            val memberId = wallyUser!!.member.id
            val filePath = Paths.get("database/linked users/$memberId/accounts/$uuid/$typeName.json")

            data = String(Files.readAllBytes(filePath))
            channel.sendMessage(ConfigManager.loadMessage( "local stats")).queue()
        } catch (e: IOException) {
            try {
                val filePath = Paths.get("database/leaderboards/$typeName/$uuid/data.json")

                data = String(Files.readAllBytes(filePath))
                channel.sendMessage(ConfigManager.loadMessage( "local stats")).queue()
            } catch (e2: IOException) {
                Wally.logger.logStackTrace(e2.stackTrace)
            }
        }
        return data
    }

    fun generateStats(data: String, file: String): Boolean {
        return try {
            val writer: Writer = FileWriter(file)
            writer.write(data)
            writer.flush()
            writer.close()
            true
        } catch (e: IOException) {
            Wally.logger.logStackTrace(e.stackTrace)
            false
        }
    }

    fun generateWallyProfile(wallyProfile: UserOption, file: String): Boolean {
        return try {
            val gson = Gson()
            val writer: Writer = FileWriter(file)

            gson.toJson(wallyProfile, writer)
            writer.flush()
            writer.close()
            true
        } catch (e: IOException) {
            Wally.logger.logStackTrace(e.stackTrace)
            false
        }
    }

    fun updateLinkedPlayerStats(data: String, type: DataType, discordID: String, uuid: String) {
        if (isLinked(discordID)) {
            val typeName = type.dataName
            val rootFolderPath = "database/linked users/$discordID"
            val playerFolderPath = "$rootFolderPath/accounts/$uuid"

            generateStats(data, "$playerFolderPath/$typeName.json")
        }
    }

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

    fun getWallyUserData(discordID: String): UserOption? {
        val gson = Gson()

        return try {
            val reader = FileReader("database/linked users/$discordID/data.json")
            val data = gson.fromJson(reader, UserOption::class.java)

            reader.close()
            data
        } catch (e: FileNotFoundException) {
            Wally.logger.logStackTrace(e.stackTrace)
            null
        }
    }

    fun isLinked(discordID: String): Boolean {
        val rootFolderPath = "database/linked users/$discordID"

        return File(rootFolderPath).exists()
    }

    fun isLinkedUUID(uuid: String): Boolean {
        return getWallyUserFromUUID(uuid) != null
    }

    fun getWallyUserFromUUID(uuid: String): WallyUser? {
        val wallyUser = Wally.users.stream().filter { user: WallyUser ->
            val memberId = user.member.id
            val folder = File("database/linked users/$memberId/accounts/")
            val entries = folder.list() ?: return@filter false

            entries.contains(uuid)
        }.findFirst()
        return wallyUser.orElse(null)
    }
}