package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.Wally
import java.io.File

class ButtonReloadUsers : ButtonExecutable() {
    
    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val rootFolder = File("database/linked users/")
        val missingDatabase = ConfigManager.loadMessage( "no user db")
        if (!rootFolder.exists()) {
            event.channel.sendMessage(missingDatabase).queue()
            return
        }

        val entries = rootFolder.list()
        if (entries == null) {
            event.channel.sendMessage(missingDatabase).queue()
            return
        }

        Wally.loadUser()
        event.channel.sendMessage(ConfigManager.loadMessage( "user reloaded")
            .replace("%users%", Wally.users.size.toString())
            .replace("%total%", entries.size.toString())).queue()
        event.message.delete().queue()
    }
}