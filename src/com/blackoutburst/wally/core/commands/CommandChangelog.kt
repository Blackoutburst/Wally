package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.EmbedUtils.generateChangelog
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.TextChannel
import com.blackoutburst.wally.core.Wally
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.File

class CommandChangelog : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (!File("config/changelog.txt").exists()) {
            message.reply(ConfigManager.loadValue("replies", "no changelog")).queue()
            return
        }

        val embed = generateChangelog()
        if (embed == null) {
            sendLogButtonReply(commandSender, message, ConfigManager.loadValue("replies", "changelog error"))
            return
        }

        val changelogChanel: TextChannel? = Wally.hitwDiscord?.getTextChannelById(ConfigManager.loadValue("channels", "changelog channel"))

        if (changelogChanel == null) {
            message.reply(ConfigManager.loadValue("replies", "no changelog channel")).queue()
            return
        }

        changelogChanel.sendMessageEmbeds(embed.build()).queue()
        message.delete().queue()
    }
}