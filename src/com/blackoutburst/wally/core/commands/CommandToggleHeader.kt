package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.MessageUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

class CommandToggleHeader : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id
        if (!DatabaseUtils.isLinked(discordID)) {
            Wally.logger.log(LogLevel.WARN, "User not linked")
            message.reply(ConfigManager.loadMessage( "you are not linked")).queue()
            return
        }

        val option = DatabaseUtils.getWallyUserData(discordID)
        if (option == null) {
            Wally.logger.log(LogLevel.WARN, "User data are missing")
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage("user data error"))
            return
        }

        val header = !option.useHeader
        option.useHeader = header

        DatabaseUtils.generateWallyProfile(option, "database/linked users/$discordID/data.json")
        Wally.loadUser()

        message.reply(ConfigManager.loadMessage( "header update").replace("%state%", if (option.useHeader) { "enabled" } else { "disabled" })).queue()
    }
}