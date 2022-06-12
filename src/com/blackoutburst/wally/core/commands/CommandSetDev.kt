package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.MessageUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

class CommandSetDev : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 2) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "setDev [discordID/Minecraft name] [true/false]")).queue()
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

        val option = DatabaseUtils.getWallyUserData(discordID)
        if (option == null) {
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage("not linked or corrupted"))
            return
        }

        option.isDeveloper = args[1].toBoolean()

        DatabaseUtils.generateWallyProfile(option, "database/linked users/$discordID/data.json")
        Wally.loadUser()

        message.reply(ConfigManager.loadMessage( "subtitle update")
            .replace("%user%", "<@$discordID>")).queue()
    }
}