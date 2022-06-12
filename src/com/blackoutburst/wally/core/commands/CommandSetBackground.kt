package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.DatabaseUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandSetBackground : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (message.attachments.size == 0) {
            message.reply(ConfigManager.loadMessage( "missing background attachment")).queue()
            return
        }

        val discordID = commandSender.member.id
        if (!DatabaseUtils.isLinked(discordID)) {
            message.reply(ConfigManager.loadMessage( "you are not linked")).queue()
            return
        }

        val file = message.attachments[0]
        if (file.fileExtension == "png" || file.fileExtension == "jpg" || file.fileExtension == "jpeg") {
            file.downloadToFile(File("database/linked users/$discordID/resources/background.png")).join()
            message.reply(ConfigManager.loadMessage( "background update")).queue()
        } else {
            message.reply(ConfigManager.loadMessage( "not a picture")).queue()
        }
    }
}