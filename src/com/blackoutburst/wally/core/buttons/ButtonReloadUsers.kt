package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import java.io.File

class ButtonReloadUsers : ButtonExecutable() {
    
    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val rootFolder = File("database/linked users/")
        if (!rootFolder.exists()) {
            Wally.logger.log(LogLevel.WARN,"The database is missing!")
            event.channel.sendMessage(ConfigManager.loadMessage( "no user db")).queue()
            return
        }

        val entries = rootFolder.list()
        if (entries == null) {
            Wally.logger.log(LogLevel.WARN,"The database is empty!")
            event.channel.sendMessage(ConfigManager.loadMessage( "user db empty")).queue()
            return
        }

        Wally.loadUser()
        event.channel.sendMessage(ConfigManager.loadMessage( "user reloaded")
            .replace("%users%", Wally.users.size.toString())
            .replace("%total%", entries.size.toString())).queue()
        event.message.delete().queue()
    }
}