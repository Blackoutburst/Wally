package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandGetChangelog : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (!File("config/changelog.txt").exists()) {
            Wally.logger.log(LogLevel.WARN, "Changelog file is missing")
            message.reply(ConfigManager.loadMessage( "no changelog")).queue()
            return
        }
        channel.sendFile(File("config/changelog.txt")).queue()
    }
}