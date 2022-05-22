package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.io.FileReader
import java.io.FileWriter
import com.blackoutburst.wally.core.Wally
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.FileNotFoundException
import java.io.Writer

class CommandSetPrefix : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", CommandManager.prefix + "setPrefix [anything]")).queue()
            return
        }
        
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json: JsonObject = gson.fromJson(FileReader(ConfigManager.configurationFile), JsonObject::class.java)
            val writer: Writer = FileWriter(ConfigManager.configurationFile.path)

            json.getAsJsonObject("settings").addProperty("command prefix", args[0])
            writer.write(gson.toJson(json))
            writer.flush()
            writer.close()
            channel.sendMessage(ConfigManager.loadMessage( "prefix update").replace("%prefix%", args[0])).queue()
            CommandManager.prefix = args[0]
        } catch (e: FileNotFoundException) {
            Wally.logger.logStackTrace(e.stackTrace)
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "prefix update error"))
        }
    }
}