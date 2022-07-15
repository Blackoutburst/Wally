package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.database.DatabaseManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.utils.CommandUtils
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandUnlink : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage").replace("%usage%", CommandManager.prefix + "unlink [discordID/Minecraft name]")).queue()
            return
        }

        var discordID = CommandUtils.getDiscordIDFromArg(guild, args[0])

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

        val folder = File("database/linked users/" + discordID)
        if (!isLinked(discordID)) {
            Wally.logger.log(LogLevel.WARN, "User is not linked")
            message.reply(ConfigManager.loadMessage( "unlink not link")).queue()
            return
        }

        if (!DatabaseManager.cleanFolder(folder)) {
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "unlink error"))
            return
        }

        channel.sendMessage(ConfigManager.loadMessage( "unlink success")).queue()
        Wally.logger.log(LogLevel.INFO, args[0] + " got unlinked")
        Wally.loadUser()
    }
}