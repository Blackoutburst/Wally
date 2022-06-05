package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.core.request.Hypixel.getPlayerStats
import com.blackoutburst.wally.utils.DatabaseUtils.isLinkedUUID
import com.blackoutburst.wally.utils.DatabaseUtils.getBackupLocalData
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.utils.DatabaseUtils.updateLeaderboardStats
import com.blackoutburst.wally.utils.MessageUtils.sendHypixelCompare
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

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
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        var player1Data = getPlayerStats(player1Uuid)
        if (player1Data == null && isLinkedUUID(player1Uuid)) {
            player1Data = getBackupLocalData(player1Uuid, DatabaseUtils.DataType.HYPIXEL, channel)
        }

        var player2Data = getPlayerStats(player2Uuid)
        if (player2Data == null && isLinkedUUID(player2Uuid)) {
            player2Data = getBackupLocalData(player2Uuid, DatabaseUtils.DataType.HYPIXEL, channel)
        }

        if (player1Data == null || player2Data == null) {
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "command compare error"))
            return
        }

        updateLeaderboardStats(player1Data, DatabaseUtils.DataType.HYPIXEL, player1Uuid)
        updateLeaderboardStats(player2Data, DatabaseUtils.DataType.HYPIXEL, player2Uuid)
        sendHypixelCompare(commandSender, message, player1Data, player2Data)
    }
}