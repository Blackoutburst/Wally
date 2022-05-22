package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.CommandUtils.createUserData
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandAddAlt : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 2) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", prefix + "addAlt [discordID] [Minecraft Name]")
            ).queue()
            return
        }

        val discordID = getDiscordIDFromTwoArgs(guild, args)
        if (discordID == null) {
            message.reply(ConfigManager.loadMessage( "not an user")).queue()
            return
        }

        if (!isLinked(discordID)) {
            message.reply(ConfigManager.loadMessage( "not linked").replace("%user%", "<@$discordID>")).queue()
            return
        }

        createUserData(true, guild, args, message, discordID, channel, commandSender)
    }
}