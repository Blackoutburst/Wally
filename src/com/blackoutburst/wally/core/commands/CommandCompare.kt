package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Hypixel.getPlayerStats
import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.getBackupLocalData
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.DatabaseUtils.updateLeaderboardStats
import com.blackoutburst.wally.utils.MessageUtils.sendHypixelCompare
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

class CommandCompare : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id

        if (args.isEmpty() || !isLinked(discordID) && args.size == 1) {
            message.reply(ConfigManager.loadMessage( "invalid command usage").replace("%usage%", CommandManager.prefix + "compare [Minecraft name] [Minecraft name]")).queue()
            return
        }

        val player1Uuid :String? = if (isLinked(discordID) && args.size == 1) {
            val data = getWallyUserData(discordID)
            data!!.mainAccountUUID
        } else {
            getPlayerUUID(args[0])
        }

        val player2Uuid :String? = if (!isLinked(discordID) || args.size == 2) {
            getPlayerUUID(args[1])
        } else {
            getPlayerUUID(args[0])
        }


        if (player1Uuid == null || player2Uuid == null) {
            Wally.logger.log(LogLevel.WARN, "One or both players are null")
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        var player1Data = getPlayerStats(player1Uuid)
        if (player1Data == null) {
            Wally.logger.log(LogLevel.INFO, "Player 1 data is null")
            player1Data = getBackupLocalData(player1Uuid, DatabaseUtils.DataType.HYPIXEL, channel)
        }

        var player2Data = getPlayerStats(player2Uuid)
        if (player2Data == null) {
            Wally.logger.log(LogLevel.INFO, "Player 2 data is null")
            player2Data = getBackupLocalData(player2Uuid, DatabaseUtils.DataType.HYPIXEL, channel)
        }

        if (player1Data == null || player2Data == null) {
            Wally.logger.log(LogLevel.INFO, "One or both player data are missing")
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "command compare error"))
            return
        }

        updateLeaderboardStats(player1Data, DatabaseUtils.DataType.HYPIXEL, player1Uuid)
        updateLeaderboardStats(player2Data, DatabaseUtils.DataType.HYPIXEL, player2Uuid)
        sendHypixelCompare(commandSender, message, player1Data, player2Data)
    }
}