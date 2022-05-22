package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.MessageUtils.concatArgs
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandSay : CommandExecutable() {
    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "say [message]")).queue()
            return
        }

        message.delete().queue()
        channel.sendMessage(concatArgs(message)).queue()
    }
}