package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandShowPracticeTracker : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        channel.sendMessage(ConfigManager.loadMessage( "practice tracker location")
            .replace("%channel%","<#" + ConfigManager.loadValue("channels", "practice tracker channel") + ">")).queue()
    }
}