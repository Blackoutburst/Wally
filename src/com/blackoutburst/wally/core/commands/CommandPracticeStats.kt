package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Mojang.getPlayerName
import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.core.request.PracticeServer.getPlayerCredits
import com.blackoutburst.wally.core.request.PracticeServer.getPlayerStats
import com.blackoutburst.wally.core.request.Visage
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.getBackupLocalData
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.DatabaseUtils.updateLeaderboardStats
import com.blackoutburst.wally.utils.DatabaseUtils.updateLinkedPlayerStats
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.utils.MessageUtils.sendPracticeStats
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

class CommandPracticeStats : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id

        val uuid: String? = if (args.isNotEmpty()) {
            if (args[0].length == 32) {
                args[0]
            } else {
                getPlayerUUID(args[0])
            }
        } else if (isLinked(discordID)) {
            val data = getWallyUserData(discordID)
            data!!.mainAccountUUID
        } else {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "practiceStats [Minecraft name]")).queue()
            return
        }

        if (uuid == null) {
            Wally.logger.log(LogLevel.WARN, "Player uuid is null")
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        val name: String? = getPlayerName(uuid)
        if (name == null) {
            Wally.logger.log(LogLevel.WARN, "Player name is null")
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        var data = getPlayerStats(uuid)
        if (data == null) {
            Wally.logger.log(LogLevel.WARN, "Data is missing")
            data = getBackupLocalData(uuid, DatabaseUtils.DataType.PRACTICE, channel)
        }

        if (data == null) {
            Wally.logger.log(LogLevel.WARN, "Data is missing")
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "command practice stats error"))
            return
        }

        var credits = getPlayerCredits(uuid)
        if (credits == null)
            credits = ""

        updateLeaderboardStats(data, DatabaseUtils.DataType.PRACTICE, uuid)
        Visage.getPlayerHead(uuid)

        sendPracticeStats(commandSender, message, PracticeStats(data, credits), uuid, false,
            (channel.id == "1017583816321290348" || channel.id == "1114857944857727026"))
    }
}