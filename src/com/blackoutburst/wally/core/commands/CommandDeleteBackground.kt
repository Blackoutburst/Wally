package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandDeleteBackground : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "deleteFontColor [discordID/Minecraft name]")).queue()
            return
        }

        var discordID = getDiscordIDFromTwoArgs(guild, args)

        if (discordID == null) {
            val playerUuid = Mojang.getPlayerUUID(args[0])
            if (playerUuid == null) {
                message.reply(ConfigManager.loadMessage( "not an user")).queue()
                return
            }

            val wallyUser = DatabaseUtils.getWallyUserFromUUID(playerUuid)
            if (wallyUser == null) {
                message.reply(ConfigManager.loadMessage( "not linked")
                    .replace("%user%", args[0])).queue()
                return
            }

            discordID = wallyUser.member.id
        }

        if (!DatabaseUtils.isLinked(discordID)) {
            message.reply(ConfigManager.loadMessage( "not linked")
                .replace("%user%", "<@$discordID>")).queue()
            return
        }

        val file = File("database/linked users/$discordID/resources/background.png")
        if (file.exists()) {
            if (file.delete()) {
                message.reply(ConfigManager.loadMessage( "background deleted")
                    .replace("%user%", "<@$discordID>")).queue()
            } else {
                message.reply(ConfigManager.loadMessage( "background delete error")).queue()
            }
        } else {
            message.reply(ConfigManager.loadMessage( "no background")).queue()
        }

    }
}