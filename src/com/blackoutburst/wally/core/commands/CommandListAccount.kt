package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.request.Mojang.getPlayerName
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandListAccount : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "listAccounts [discordID/Minecraft name]")).queue()
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

        val folder = File("database/linked users/$discordID/accounts/")
        val entries = folder.list()
        val data = getWallyUserData(discordID!!)

        if (data == null) {
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "account list error"))
            return
        }

        val mainUUID = data.mainAccountUUID
        val builder = StringBuilder()

        builder.append("```md").append(System.lineSeparator())
            .append("#").append(getPlayerName(mainUUID)).append(System.lineSeparator())

        for (s in entries!!) {
            if (s == mainUUID) continue
            builder.append(getPlayerName(s)).append(System.lineSeparator())
        }

        builder.append("```").append(System.lineSeparator())
        channel.sendMessage(builder).queue()
    }
}