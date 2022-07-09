package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.CommandUtils.concatArgs
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.emoji.Emoji
import java.awt.Color


class CommandSuggest : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", CommandManager.prefix + "say [message]")).queue()
            return
        }

        val iconURL = commandSender.member.user.avatarUrl
        val wallyURL = "https://github.com/Blackoutburst/Wally"

        val embed = EmbedBuilder()
            .setAuthor(commandSender.member.effectiveName, wallyURL, iconURL)
            .setTitle("New suggestion")
            .setColor(Color(71, 165, 255))
            .setDescription(concatArgs(message)).build()

        channel.sendMessageEmbeds(embed).queue{r ->
            r.addReaction(Emoji.fromUnicode("U+1F44D")).queue()
            r.addReaction(Emoji.fromUnicode("U+1F44E")).queue()
        }
        Wally.devServer?.getTextChannelById(ConfigManager.loadValue("channels", "dev suggestion channel"))?.sendMessageEmbeds(embed)?.queue()
    }
}


