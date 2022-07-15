package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.utils.CommandUtils
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.MessageUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

class CommandSetSubtitle : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "setSubtitle [discordID/Minecraft name] [Title]")).queue()
            return
        }

        var discordID = getDiscordIDFromTwoArgs(guild, args)

        if (discordID == null) {
            Wally.logger.log(LogLevel.WARN, "Discord id is null")
            val playerUuid = Mojang.getPlayerUUID(args[0])
            if (playerUuid == null) {
                Wally.logger.log(LogLevel.WARN, "Player uuid is null")
                message.reply(ConfigManager.loadMessage( "not an user")).queue()
                return
            }

            val wallyUser = DatabaseUtils.getWallyUserFromUUID(playerUuid)
            if (wallyUser == null) {
                Wally.logger.log(LogLevel.WARN, "Wally user is missing")
                message.reply(ConfigManager.loadMessage( "not linked")
                    .replace("%user%", args[0])).queue()
                return
            }

            discordID = wallyUser.member.id
        }

        if (!DatabaseUtils.isLinked(discordID)) {
            Wally.logger.log(LogLevel.WARN, "User is not linked")
            message.reply(ConfigManager.loadMessage( "not linked")
                .replace("%user%", "<@$discordID>")).queue()
            return
        }

        val option = DatabaseUtils.getWallyUserData(discordID)
        if (option == null) {
            Wally.logger.log(LogLevel.WARN, "User data are missing")
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage("not linked or corrupted"))
            return
        }

        if (args.size > 1) {
            option.subtitle = CommandUtils.getSubtitle(message)
        } else {
            option.subtitle = ""
        }

        DatabaseUtils.generateWallyProfile(option, "database/linked users/$discordID/data.json")
        Wally.loadUser()

        message.reply(ConfigManager.loadMessage( "subtitle update")
            .replace("%user%", "<@$discordID>")).queue()
    }
}