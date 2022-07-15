package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.database.DatabaseManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromArg
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.FilesUtils.removeExclusion
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

class CommandRemoveAlt : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 2) {
            message.reply(
                ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "removeAlt [discordID/Minecraft name] [Alt Minecraft name]")
            ).queue()
            return
        }

        var discordID = getDiscordIDFromArg(guild, args[0])

        if (discordID == null) {
            Wally.logger.log(LogLevel.WARN, "Discord id is null")
            val playerUuid = getPlayerUUID(args[0])
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

        if (!isLinked(discordID)) {
            Wally.logger.log(LogLevel.WARN, "User is not linked")
            message.reply(
                ConfigManager.loadMessage( "not linked").replace("%user%", "<@$discordID>")
            ).queue()
            return
        }

        val playerName = args[1]
        val playerUUID = getPlayerUUID(playerName)
        val folder = File("database/linked users/$discordID/accounts/$playerUUID")

        if (!folder.exists()) {
            message.reply(ConfigManager.loadMessage( "not an alt account")
                    .replace("%name%", playerName)
                    .replace("%user%", "<@$discordID>"))
                .setActionRow(
                    Button.primary(commandSender.member.id + ":" + message.id, "Yes run listAccounts"),
                    Button.danger(commandSender.member.id + ":" + UUID.randomUUID(), "No"))
                .queue { r: Message ->
                    r.delete().queueAfter(
                        ConfigManager.loadValue("settings", "answer lifetime").toLong(),
                        TimeUnit.SECONDS
                    )
                }
            return
        }

        if (!DatabaseManager.cleanFolder(folder)) {
            sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "removed alt error"))
            return
        }

        removeExclusion(playerUUID)
        channel.sendMessage(
            ConfigManager.loadMessage( "remove alt success")
                .replace("%name%", playerName)
                .replace("%user%", "<@$discordID>")
        ).queue()
        Wally.logger.log(LogLevel.INFO, "$playerName got unlinked")
    }
}