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
import net.dv8tion.jda.api.entities.TextChannel
import java.io.File

class CommandChangelog : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (!File("config/changelog.txt").exists()) {
            message.reply(ConfigManager.loadMessage( "no changelog")).queue()
            return
        }

        val embed = generateChangelog()
        if (embed == null) {
            Wally.logger.log(LogLevel.ERROR,"Changelog generation failed!")
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "changelog error"))
            return
        }

        val changelogChanel: TextChannel? = Wally.hitwDiscord?.getTextChannelById(ConfigManager.loadValue("channels", "changelog channel"))

        if (changelogChanel == null) {
            Wally.logger.log(LogLevel.WARN,"Changelog channel doesn't exist!")
            message.reply(ConfigManager.loadMessage( "no changelog channel")).queue()
            return
        }

        changelogChanel.sendMessageEmbeds(embed.build()).queue()
        message.delete().queue()
    }
}