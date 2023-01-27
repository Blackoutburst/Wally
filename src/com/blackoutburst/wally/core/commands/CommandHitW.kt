package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Hypixel.getPlayerCount
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.awt.Color
import java.time.LocalDate

class CommandHitW : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        val embed = EmbedBuilder()
            .setAuthor("How dead is the game ?", wallyURL, iconURL)
            .setTitle("${getPlayerCount()} players are playing HitW right now!")
            .setColor(Color(71, 165, 255))
            .setFooter("Date: " + LocalDate.now())

        Wally.hitwDiscord!!.getVoiceChannelById(ConfigManager.loadValue("channels", "player count"))!!.manager.setName("${getPlayerCount()} playing HitW").queue()
        channel.sendMessageEmbeds(embed.build()).queue()
    }
}