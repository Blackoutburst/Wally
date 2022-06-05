package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.core.request.Hypixel.getPlayerStats
import com.blackoutburst.wally.utils.DatabaseUtils.isLinkedUUID
import com.blackoutburst.wally.utils.DatabaseUtils.getBackupLocalData
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.utils.DatabaseUtils.updateLeaderboardStats
import com.blackoutburst.wally.utils.DatabaseUtils.updateLinkedPlayerStats
import com.blackoutburst.wally.utils.MessageUtils.sendHypixelStats
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Visage
import com.blackoutburst.wally.core.statsobject.HypixelStats
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandStats : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id

        val uuid: String? = if (args.isNotEmpty()) {
            getPlayerUUID(args[0])
        } else if (isLinked(discordID)) {
            val data = getWallyUserData(discordID)
            data!!.mainAccountUUID
        } else {
            message.reply(ConfigManager.loadMessage( "invalid command usage").replace("%usage%", CommandManager.prefix + "stats [Minecraft name]")).queue()
            return
        }

        if (uuid == null) {
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        var data = getPlayerStats(uuid)
        if (data == null && isLinkedUUID(uuid)) {
            data = getBackupLocalData(uuid, DatabaseUtils.DataType.HYPIXEL, channel)
        }

        if (data == null) {
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "command stats error"))
            return
        }
        updateLeaderboardStats(data, DatabaseUtils.DataType.HYPIXEL, uuid)
        updateLinkedPlayerStats(data, DatabaseUtils.DataType.HYPIXEL, discordID, uuid)
        Visage.getPlayerHead(uuid)
        sendHypixelStats(commandSender, message, HypixelStats(data), uuid, false)
    }
}