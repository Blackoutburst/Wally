package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.threads.HypixelTracker
import com.blackoutburst.wally.core.threads.PracticeTracker
import com.blackoutburst.wally.utils.CommandUtils
import com.blackoutburst.wally.utils.DatabaseUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

class CommandForcePB : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", CommandManager.prefix + "forcePB [discordID/Minecraft name]")).queue()
            return
        }

        var discordID = CommandUtils.getDiscordIDFromTwoArgs(guild, args)

        if (discordID == null) {
            val playerUuid = Mojang.getPlayerUUID(args[0])
            if (playerUuid == null) {
                message.reply(ConfigManager.loadMessage( "not an user")).queue()
                return
            }

            val wallyUser = DatabaseUtils.getWallyUserFromUUID(playerUuid)
            if (wallyUser == null) {
                message.reply(
                    ConfigManager.loadMessage( "not linked")
                    .replace("%user%", args[0])).queue()
                return
            }

            discordID = wallyUser.member.id
        }

        val options = DatabaseUtils.getWallyUserData(discordID)
        if (options == null) {
            message.reply(
                ConfigManager.loadMessage( "not linked")
                .replace("%user%", args[0])).queue()
            return
        }
        val user = DatabaseUtils.getWallyUserFromUUID(options.mainAccountUUID)
        if (user == null) {
            message.reply(
                ConfigManager.loadMessage( "not linked")
                    .replace("%user%", args[0])).queue()
            return
        }

        if (!HypixelTracker.checkPlayer(user) && !PracticeTracker.checkPlayer(user)) {
            channel.sendMessage(ConfigManager.loadMessage("no pb").replace("%user%", user.member.effectiveName)).queue()
        } else {
            channel.sendMessage(ConfigManager.loadMessage("pb display").replace("%user%", user.member.effectiveName)).queue()
        }
    }
}


