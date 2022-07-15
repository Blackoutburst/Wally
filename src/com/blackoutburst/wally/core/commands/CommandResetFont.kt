package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.utils.DatabaseUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandResetFont : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id
        if (!DatabaseUtils.isLinked(discordID)) {
            Wally.logger.log(LogLevel.WARN, "User is not linked")
            message.reply(ConfigManager.loadMessage( "you are not linked")).queue()
            return
        }

        val file = File("database/linked users/${commandSender.member.id}/resources/font.ttf")
        if (file.exists()) {
            if (file.delete()) {
                message.reply(ConfigManager.loadMessage( "font reset")).queue()
            } else {
                message.reply(ConfigManager.loadMessage( "font delete error")).queue()
            }
        } else {
            message.reply(ConfigManager.loadMessage( "no font")).queue()
        }
    }
}