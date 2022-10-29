package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.FileReader
import java.io.FileWriter
import java.io.Writer

class CommandSetPrefix : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", CommandManager.prefix + "setPrefix [New prefix]")).queue()
            return
        }
        
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val fileReader = FileReader(ConfigManager.configurationFile)
            val json: JsonObject = gson.fromJson(fileReader, JsonObject::class.java)
            val writer: Writer = FileWriter(ConfigManager.configurationFile.path)

            json.getAsJsonObject("settings").addProperty("command prefix", args[0])
            writer.write(gson.toJson(json))
            writer.flush()
            writer.close()
            fileReader.close()
            channel.sendMessage(ConfigManager.loadMessage( "prefix update").replace("%prefix%", args[0])).queue()
            CommandManager.prefix = args[0]
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "prefix update error"))
        }
    }
}