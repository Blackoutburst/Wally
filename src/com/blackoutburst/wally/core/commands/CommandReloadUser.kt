package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.Wally
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.File

class CommandReloadUser : CommandExecutable() {
    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val rootFolder = File("database/linked users/")

        if (!rootFolder.exists()) {
            message.reply(ConfigManager.loadValue("replies", "no user db")).queue()
            return
        }

        val entries = rootFolder.list()
        if (entries == null) {
            message.reply(ConfigManager.loadValue("replies", "no user db")).queue()
            return
        }

        Wally.loadUser()
        channel.sendMessage(ConfigManager.loadValue("replies", "user reloaded")
                .replace("%users%", java.lang.String.valueOf(Wally.users.size))
                .replace("%total%", entries.size.toString())).queue()
    }
}