package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.core.request.Visage
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.getBackupLocalData
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.DatabaseUtils.updateLeaderboardStats
import com.blackoutburst.wally.utils.DatabaseUtils.updateLinkedPlayerStats
import com.blackoutburst.wally.utils.MessageUtils.sendCrossCompare
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

class CommandCrossCompare : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id

        val uuid: String? = if (args.isNotEmpty()) {
            getPlayerUUID(args[0])
        } else if (isLinked(discordID)) {
            val data = getWallyUserData(discordID)
            data!!.mainAccountUUID
        } else {
            message.reply(ConfigManager.loadMessage( "invalid command usage").replace("%usage%", CommandManager.prefix + "crossCompare [Minecraft name]")).queue()
            return
        }

        if (uuid == null) {
            Wally.logger.log(LogLevel.WARN, "UUID is null")
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        var data = Hypixel.getPlayerStats(uuid)
        if (data == null) {
            Wally.logger.log(LogLevel.WARN, "Data is null")
            data = getBackupLocalData(uuid, DatabaseUtils.DataType.HYPIXEL, channel)
        }

        var practiceData = PracticeServer.getPlayerStats(uuid)
        if (practiceData == null) {
            Wally.logger.log(LogLevel.WARN, "Practice data is null")
            practiceData = getBackupLocalData(uuid, DatabaseUtils.DataType.PRACTICE, channel)
        }

        if (data == null || practiceData == null) {
            Wally.logger.log(LogLevel.WARN, "Hypixel, practice or both data are null")
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "command stats error"))
            return
        }
        updateLeaderboardStats(data, DatabaseUtils.DataType.HYPIXEL, uuid)
        updateLeaderboardStats(practiceData, DatabaseUtils.DataType.PRACTICE, uuid)

        val wallyUser = DatabaseUtils.getWallyUserFromUUID(uuid)
        if (wallyUser != null) {
            Wally.logger.log(LogLevel.WARN, "Wally user is missing")
            updateLinkedPlayerStats(data, DatabaseUtils.DataType.HYPIXEL, wallyUser.member.id, uuid)
            updateLinkedPlayerStats(practiceData, DatabaseUtils.DataType.PRACTICE, wallyUser.member.id, uuid)
        }
        Visage.getPlayerHead(uuid)
        sendCrossCompare(commandSender, message, HypixelStats(data), PracticeStats(practiceData), uuid, false)
    }
}