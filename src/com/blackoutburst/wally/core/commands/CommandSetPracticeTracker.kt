package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.io.FileReader
import com.blackoutburst.wally.core.config.ConfigManager
import java.io.FileWriter
import com.blackoutburst.wally.core.Wally
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.Writer

class CommandSetPracticeTracker : CommandExecutable() {
    override fun onCommand(
        commandSender: WallyUser,
        commandName: String,
        args: Array<String>,
        guild: Guild,
        channel: MessageChannel,
        message: Message
    ) {
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val fileReader = FileReader(ConfigManager.configurationFile)
            val json: JsonObject = gson.fromJson(fileReader, JsonObject::class.java)
            val writer: Writer = FileWriter(ConfigManager.configurationFile.path)

            json.getAsJsonObject("channels").addProperty("practice tracker channel", channel.id)
            writer.write(gson.toJson(json))
            writer.flush()
            writer.close()
            fileReader.close()
            channel.sendMessage(ConfigManager.loadMessage( "practice tracker channel updated")
                    .replace("%channel%", "<#" + channel.id + ">")).queue()
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "channel update error"))
        }
    }
}