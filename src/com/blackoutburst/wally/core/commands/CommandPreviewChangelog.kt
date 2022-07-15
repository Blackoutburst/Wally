package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.utils.EmbedUtils.generateChangelog
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandPreviewChangelog : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (!File("config/changelog.txt").exists()) {
            message.reply(ConfigManager.loadMessage( "no changelog")).queue()
            return
        }

        val embed = generateChangelog()
        if (embed == null) {
            Wally.logger.log(LogLevel.WARN, "Changelog embed is missing")
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "changelog error"))
            return
        }
        channel.sendMessageEmbeds(embed.build()).queue()
        message.delete().queue()
    }
}