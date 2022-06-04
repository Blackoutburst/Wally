package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import java.io.BufferedReader
import java.io.FileReader
import com.blackoutburst.wally.core.Wally
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.File
import java.lang.StringBuilder

class CommandExcludeList : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (!File("config/excluded.txt").exists()) {
            message.reply(ConfigManager.loadMessage( "no exclude file")).queue()
            return
        }

        try {
            val reader = BufferedReader(FileReader("config/excluded.txt"))
            val builder = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                builder.append(line).append("\n")
            }
            reader.close()

            channel.sendMessage(builder).queue()
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "exclude error"))
        }
    }
}