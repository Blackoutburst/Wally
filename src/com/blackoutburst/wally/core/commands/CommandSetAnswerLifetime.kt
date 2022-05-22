package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import java.lang.Exception
import com.blackoutburst.wally.core.Wally
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.FileReader
import java.io.FileWriter
import java.io.Writer

class CommandSetAnswerLifetime : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(ConfigManager.loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "setAnswerLifetime [seconds]")).queue()
            return
        }

        val value: Long = try {
            args[0].toLong()
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e.stackTrace)
            message.reply(ConfigManager.loadValue("replies", "not a number")
                    .replace("%num%", args[0])).queue()
            return
        }

        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json: JsonObject = gson.fromJson(
                FileReader(ConfigManager.configurationFile),
                JsonObject::class.java
            )
            val writer: Writer = FileWriter(ConfigManager.configurationFile.path)
            json.getAsJsonObject("settings").addProperty("answer lifetime", value)
            writer.write(gson.toJson(json))
            writer.flush()
            writer.close()
            channel.sendMessage(ConfigManager.loadValue("replies", "answer life update")
                    .replace("%seconds%", args[0])).queue()
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e.stackTrace)
            sendLogButtonReply(commandSender, message, ConfigManager.loadValue("replies", "answer life error"))
        }
    }
}