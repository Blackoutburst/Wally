package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.CommandUtils.concatArgs
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandSay : CommandExecutable() {
    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "say [message]")).queue()
            return
        }

        val attachments : MutableList<File> = ArrayList()
        for (a in message.attachments) {
            attachments.add(a.downloadToFile(File("database/attachments/" + a.fileName)).get())
        }

        val newMessage = channel.sendMessage(concatArgs(message))
        for (a in attachments) {
            newMessage.addFile(a)
        }
        newMessage.queue()

        message.delete().queue()
    }
}