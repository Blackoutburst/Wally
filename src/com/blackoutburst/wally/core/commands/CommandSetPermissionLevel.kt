package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.UUID
import java.util.concurrent.TimeUnit

class CommandSetPermissionLevel : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                    .replace("%usage%", CommandManager.prefix + "setPermissionLevel [discordID]")).queue()
            return
        }

        val discordID = getDiscordIDFromTwoArgs(guild, args)
        if (discordID == null) {
            message.reply(ConfigManager.loadMessage( "not an user")).queue()
            return
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