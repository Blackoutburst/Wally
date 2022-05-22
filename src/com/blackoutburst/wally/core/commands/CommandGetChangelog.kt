package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.File

class CommandGetChangelog : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (!File("config/changelog.txt").exists()) {
            message.reply(ConfigManager.loadMessage( "no changelog")).queue()
            return
        }
        channel.sendFile(File("config/changelog.txt")).queue()
    }
}