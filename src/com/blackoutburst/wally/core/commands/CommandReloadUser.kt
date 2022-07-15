package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandReloadUser : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val rootFolder = File("database/linked users/")

        if (!rootFolder.exists()) {
            message.reply(ConfigManager.loadMessage( "no user db")).queue()
            return
        }

        val entries = rootFolder.list()
        if (entries == null) {
            message.reply(ConfigManager.loadMessage( "user db empty")).queue()
            return
        }

        Wally.loadUser()
        channel.sendMessage(ConfigManager.loadMessage( "user reloaded")
                .replace("%users%", java.lang.String.valueOf(Wally.users.size))
                .replace("%total%", entries.size.toString())).queue()
    }
}