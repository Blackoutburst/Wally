package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.core.request.Visage
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.MessageUtils
import com.blackoutburst.wally.utils.MessageUtils.sendPGraph
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel


class CommandPGraph : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id

        val uuid: String? = if (args.isNotEmpty()) {
            Mojang.getPlayerUUID(args[0])
        } else if (DatabaseUtils.isLinked(discordID)) {
            val data = DatabaseUtils.getWallyUserData(discordID)
            data!!.mainAccountUUID
        } else {
            message.reply(ConfigManager.loadMessage( "invalid command usage").replace("%usage%", CommandManager.prefix + "pGraph [Minecraft name]")).queue()
            return
        }

        if (uuid == null) {
            Wally.logger.log(LogLevel.WARN, "Player uuid is null")
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        var data = PracticeServer.getPlayerStats(uuid)
        if (data == null) {
            Wally.logger.log(LogLevel.WARN, "Player data are missing")
            data = DatabaseUtils.getBackupLocalData(uuid, DatabaseUtils.DataType.PRACTICE, channel)
        }

        if (data == null) {
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage("command stats error"))
            return
        }
        DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.PRACTICE, uuid)

        val wallyUser = DatabaseUtils.getWallyUserFromUUID(uuid)
        if (wallyUser != null) {
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.PRACTICE, wallyUser.member.id, uuid)
        }
        Visage.getPlayerHead(uuid)

        sendPGraph(commandSender, data, message, "qualification_history", "Qualification", false)
    }
}