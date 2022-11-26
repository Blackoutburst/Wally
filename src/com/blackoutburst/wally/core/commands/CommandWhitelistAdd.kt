package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.PracticeServer
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.lang.management.ManagementFactory
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class CommandWhitelistAdd : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(ConfigManager.loadMessage( "invalid command usage").replace("%usage%", CommandManager.prefix + "whitelistAdd [Minecraft Name]")).queue()
            return
        }

        val res = PracticeServer.whitelistAdd(args[0])

        if (res == null) {
            message.reply(ConfigManager.loadMessage("whitelist error")).queue()
        } else {
            message.reply(res).queue()
        }
    }
}


