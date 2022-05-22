package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.database.DatabaseManager
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.File

class CommandUnlink : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(
                ConfigManager.loadValue("replies", "invalid command usage").replace("%usage%", CommandManager.prefix + "unlink [discordID]")).queue()
            return
        }

        val folder = File("database/linked users/" + args[0])
        if (!isLinked(args[0])) {
            message.reply(ConfigManager.loadValue("replies", "unlink not link")).queue()
            return
        }

        if (!DatabaseManager.cleanFolder(folder)) {
            sendLogButtonReply(commandSender, message, ConfigManager.loadValue("replies", "unlink error"))
            return
        }

        channel.sendMessage(ConfigManager.loadValue("replies", "unlink success")).queue()
        Wally.logger.log(LogLevel.INFO, args[0] + " got unlinked")
    }
}