package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.File

/**
 * Update the changelog file
 */
class CommandSetChangelog : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (message.attachments.size == 0) {
            message.reply(ConfigManager.loadMessage( "missing config attachment")).queue()
        }

        val file = message.attachments[0]
        if (file.fileExtension == "txt") {
            file.proxy.downloadToFile(File("config/changelog.txt"))
            channel.sendMessage(ConfigManager.loadMessage( "changelog updated")).queue()
        } else {
            message.reply(ConfigManager.loadMessage( "invalid changelog format")).queue()
        }
    }
}