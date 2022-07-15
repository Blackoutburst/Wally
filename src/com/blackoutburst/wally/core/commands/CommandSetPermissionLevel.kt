package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*
import java.util.concurrent.TimeUnit

class CommandSetPermissionLevel : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "setPermissionLevel [discordID/Minecraft name]")).queue()
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

        val member = guild.getMemberById(discordID)
        val data = getWallyUserData(discordID)
        val id = commandSender.member.id + ":" + discordID + ":"

        message.reply(ConfigManager.loadMessage( "select permission level")
            .replace("%user%", member!!.effectiveName)).setActionRow(
            if (data!!.permissionLevel == PermissionLevel.MEMBER) Button.primary(id + UUID.randomUUID(),"MEMBER") else Button.secondary(id + UUID.randomUUID(), "MEMBER"),
            if (data.permissionLevel == PermissionLevel.STAFF) Button.primary(id + UUID.randomUUID(),"STAFF") else Button.secondary(id + UUID.randomUUID(), "STAFF"),
            if (data.permissionLevel == PermissionLevel.ADMIN) Button.primary(id + UUID.randomUUID(),"ADMIN") else Button.secondary(id + UUID.randomUUID(), "ADMIN")
        ).queue { r: Message ->
            r.delete().queueAfter(ConfigManager.loadValue("settings", "answer lifetime").toLong(), TimeUnit.SECONDS)
        }
    }
}