package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandSetConfig : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (message.attachments.size == 0) {
            message.reply(ConfigManager.loadMessage( "missing config attachment")).queue()
            return
        }

        val file = message.attachments[0]
        if (file.fileExtension == "json") {
            file.downloadToFile(File("config/config.json")).join()
            channel.sendMessage(ConfigManager.loadMessage( "config updated")).queue()
        } else {
            message.reply(ConfigManager.loadMessage( "not a json")).queue()
        }
    }
}