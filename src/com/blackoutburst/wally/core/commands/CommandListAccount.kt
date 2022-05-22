package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.config.ConfigManager.loadValue
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.core.request.Mojang.getPlayerName
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.io.File
import java.lang.StringBuilder

class CommandListAccount : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(loadValue("replies", "invalid command usage")
                .replace("%usage%", prefix + "listAccounts [discordID]")).queue()
            return
        }

        val discordID = getDiscordIDFromTwoArgs(guild, args)
        val folder = File("database/linked users/$discordID/accounts/")
        val entries = folder.list()
        val data = getWallyUserData(discordID!!)

        if (data == null) {
            sendLogButtonReply(commandSender, message, loadValue("replies", "account list error"))
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