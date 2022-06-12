package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.CommandUtils.createUserData
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.core.request.Visage
import com.blackoutburst.wally.utils.CommandUtils.getNonDiscordIDArgFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.FilesUtils
import com.blackoutburst.wally.utils.MessageUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.awt.Color
import java.io.File

class CommandResetFontColor : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id
        if (DatabaseUtils.isLinked(discordID)) {
            message.reply(ConfigManager.loadMessage( "you are not linked")).queue()
            return
        }

        val option = DatabaseUtils.getWallyUserData(discordID)
        if (option == null) {
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage("user data error"))
            return
        }

        option.fontColor = "#FFFFFF"

        DatabaseUtils.generateWallyProfile(option, "database/linked users/$discordID/data.json")
        Wally.loadUser()

        message.reply(ConfigManager.loadMessage( "font color reset")).queue()
    }
}