package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.UUID
import java.util.concurrent.TimeUnit

class CommandSetLogLevel : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val id = commandSender.member.id + ":"

        message.reply(ConfigManager.loadValue("replies", "select log level")).setActionRow(
            if (Wally.logger.internalLogLevel == LogLevel.INFO) Button.primary(id + UUID.randomUUID(), "INFO") else Button.secondary(id + UUID.randomUUID(), "INFO"),
            if (Wally.logger.internalLogLevel == LogLevel.WARN) Button.primary(id + UUID.randomUUID(), "WARN") else Button.secondary(id + UUID.randomUUID(), "WARN"),
            if (Wally.logger.internalLogLevel == LogLevel.ERROR) Button.primary(id + UUID.randomUUID(), "ERROR") else Button.secondary(id + UUID.randomUUID(), "ERROR"),
            if (Wally.logger.internalLogLevel == LogLevel.NONE) Button.primary(id + UUID.randomUUID(), "NONE") else Button.secondary(id + UUID.randomUUID(), "NONE")
        ).queue { r: Message ->
            r.delete().queueAfter(
                ConfigManager.loadValue("settings", "answer lifetime").toLong(),
                TimeUnit.SECONDS
            )
        }
    }
}