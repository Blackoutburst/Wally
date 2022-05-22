package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.utils.DatabaseUtils.updateUserDisplayPreference
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import com.blackoutburst.wally.core.commands.DisplayPreference
import com.blackoutburst.wally.core.config.ConfigManager
import java.util.Locale
import java.util.UUID
import com.blackoutburst.wally.core.Wally
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.concurrent.TimeUnit

class ButtonDisplayPreference : ButtonExecutable() {
    
    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val discordID = event.member!!.id
        val displayPreference = DisplayPreference.valueOf(event.button.label)

        updateUserDisplayPreference(event.textChannel, discordID, displayPreference)

        val id = discordID + ":" + event.message.id

        event.reply(ConfigManager.loadMessage( "user display preference updated").replace("%preference%", displayPreference.toString().lowercase(Locale.getDefault()))).setEphemeral(true).queue()

        event.message.editMessage(ConfigManager.loadMessage( "select display preference")).setActionRow(
                if (displayPreference == DisplayPreference.CANVAS) Button.primary(id + UUID.randomUUID(),"CANVAS") else Button.secondary(id + UUID.randomUUID(), "CANVAS"),
                if (displayPreference == DisplayPreference.EMBED) Button.primary(id + UUID.randomUUID(),"EMBED") else Button.secondary(id + UUID.randomUUID(), "EMBED"),
                if (displayPreference == DisplayPreference.TEXT) Button.primary(id + UUID.randomUUID(),"TEXT") else Button.secondary(id + UUID.randomUUID(), "TEXT")
            ).queue { r: Message ->
            r.delete().queueAfter(
                ConfigManager.loadValue("settings", "answer lifetime").toLong(),
                TimeUnit.SECONDS
            )
        }
        Wally.loadUser()
    }
}