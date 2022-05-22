package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.UUID
import java.util.concurrent.TimeUnit

class CommandSetDisplayPreference : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id
        if (!isLinked(discordID)) {
            message.reply(ConfigManager.loadValue("replies", "sorry not linked")).queue()
            return
        }

        val option = getWallyUserData(discordID)
        val id = commandSender.member.id + ":"

        message.reply(ConfigManager.loadValue("replies", "select display preference")).setActionRow(
            if (option!!.displayPreference == DisplayPreference.CANVAS) Button.primary(id + UUID.randomUUID(),"CANVAS") else Button.secondary(id + UUID.randomUUID(), "CANVAS"),
            if (option.displayPreference == DisplayPreference.EMBED) Button.primary(id + UUID.randomUUID(),"EMBED") else Button.secondary(id + UUID.randomUUID(), "EMBED"),
            if (option.displayPreference == DisplayPreference.TEXT) Button.primary(id + UUID.randomUUID(),"TEXT") else Button.secondary(id + UUID.randomUUID(), "TEXT")
        ).queue { r: Message ->
            r.delete().queueAfter(
                ConfigManager.loadValue("settings", "answer lifetime").toLong(),
                TimeUnit.SECONDS
            )
        }
    }
}