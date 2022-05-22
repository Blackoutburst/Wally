package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.CommandUtils.concatArgs
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.awt.Color
import java.time.LocalDate

class CommandPoll : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "poll [message]")
            ).queue()
            return
        }

        val iconURL = commandSender.member.user.avatarUrl
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        val embed = EmbedBuilder()
            .setAuthor(commandSender.member.effectiveName, wallyURL, iconURL)
            .setTitle(concatArgs(message))
            .setColor(Color(71, 165, 255))
            .setFooter("Created on " + LocalDate.now())

        message.delete().queue()
        channel.sendMessageEmbeds(embed.build()).queue { msg: Message ->
            msg.addReaction("U+1F44D").queue()
            msg.addReaction("U+1F44E").queue()
        }
    }
}