package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.UUID
import java.util.concurrent.TimeUnit

class ButtonLogLevel : ButtonExecutable() {
    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val discordID = event.member!!.id
        val id = discordID + ":" + event.message.id
        val level = LogLevel.valueOf(event.button.label)

        Wally.logger.internalLogLevel = level

        event.reply(ConfigManager.loadMessage( "log level updated").replace("%level%", level.toString())).setEphemeral(true).queue()
        
        event.message.editMessage(ConfigManager.loadMessage( "select display preference")).setActionRow(
                if (Wally.logger.internalLogLevel == LogLevel.INFO) Button.primary(id + UUID.randomUUID(), "INFO") else Button.secondary(id + UUID.randomUUID(), "INFO"),
                if (Wally.logger.internalLogLevel == LogLevel.WARN) Button.primary(id + UUID.randomUUID(), "WARN") else Button.secondary(id + UUID.randomUUID(), "WARN"),
                if (Wally.logger.internalLogLevel == LogLevel.ERROR) Button.primary(id + UUID.randomUUID(), "ERROR") else Button.secondary(id + UUID.randomUUID(),"ERROR"),
                if (Wally.logger.internalLogLevel == LogLevel.NONE) Button.primary(id + UUID.randomUUID(), "NONE") else Button.secondary(id + UUID.randomUUID(), "NONE")
            ).queue { r: Message ->
                r.delete().queueAfter(
                ConfigManager.loadValue("settings", "answer lifetime").toLong(),
                TimeUnit.SECONDS
            )
        }
    }
}