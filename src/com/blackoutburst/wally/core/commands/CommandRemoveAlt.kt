package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.CommandUtils.getNonDiscordIDArgFromTwoArgs
import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.utils.MessageUtils.sendLogButtonReply
import com.blackoutburst.wally.utils.FilesUtils.removeExclusion
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import java.util.UUID
import com.blackoutburst.wally.core.database.DatabaseManager
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.io.File
import java.util.concurrent.TimeUnit

class CommandRemoveAlt : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 2) {
            message.reply(
                ConfigManager.loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "removeAlt [discordID]")
            ).queue()
            return
        }

        val discordID = getDiscordIDFromTwoArgs(guild, args)
        if (discordID == null) {
            message.reply(ConfigManager.loadValue("replies", "not an user")).queue()
            return
        }

        if (!isLinked(discordID)) {
            message.reply(
                ConfigManager.loadValue("replies", "not linked").replace("%user%", "<@$discordID>")
            ).queue()
            return
        }

        val playerName = getNonDiscordIDArgFromTwoArgs(guild, args)
        if (playerName == null) {
            message.reply(ConfigManager.loadValue("replies", "could not get name")).queue()
            return
        }

        val playerUUID = getPlayerUUID(playerName)
        val folder = File("database/linked users/$discordID/accounts/$playerUUID")

        if (!folder.exists()) {
            message.reply(ConfigManager.loadValue("replies", "not an alt account")
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
            sendLogButtonReply(commandSender, message, ConfigManager.loadValue("replies", "removed alt error"))
            return
        }

        removeExclusion(playerUUID)
        channel.sendMessage(
            ConfigManager.loadValue("replies", "remove alt success")
                .replace("%name%", playerName)
                .replace("%user%", "<@$discordID>")
        ).queue()
        Wally.logger.log(LogLevel.INFO, "$playerName got unlinked")
    }
}