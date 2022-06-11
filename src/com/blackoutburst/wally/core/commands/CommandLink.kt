package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.CommandUtils.createUserData
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.request.Visage
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandLink : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 2) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "link [discordID] [Minecraft Name]")).queue()
            return
        }

        val discordID = getDiscordIDFromTwoArgs(guild, args)
        if (discordID == null) {
            message.reply(ConfigManager.loadMessage( "not an user")).queue()
            return
        }
        if (Wally.users.find { user -> user.member.id == discordID } != null) {
            val user = Wally.users.first { user -> user.member.id == discordID }
            message.reply(ConfigManager.loadMessage( "already linked")
                .replace("%name%", user.member.effectiveName)
                .replace("%player%", Mojang.getPlayerName(user.mainAccountUuid) ?: user.mainAccountUuid)).queue()
            return
        }
        createUserData(false, guild, args, message, discordID, channel, commandSender)
    }
}