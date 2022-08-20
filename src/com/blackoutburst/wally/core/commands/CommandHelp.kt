package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.utils.EmbedUtils.adminCommandsPage1
import com.blackoutburst.wally.utils.EmbedUtils.helpEmbed
import com.blackoutburst.wally.utils.EmbedUtils.hypixelCommands
import com.blackoutburst.wally.utils.EmbedUtils.miscCommandsPage1
import com.blackoutburst.wally.utils.EmbedUtils.practiceCommands
import com.blackoutburst.wally.utils.EmbedUtils.staffCommandsPage1
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.util.*


class CommandHelp : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.isEmpty()) {
            if (commandSender.permissionLevel.weight >= PermissionLevel.ADMIN.weight) {
                channel.sendMessageEmbeds(helpEmbed(commandSender)).setActionRow(
                    Button.danger(commandSender.member.id + ":" + UUID.randomUUID(),"Admin commands"),
                    Button.primary(commandSender.member.id + ":" + UUID.randomUUID(),"Staff commands"),
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Hypixel commands"),
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Practice commands"),
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Misc commands")
                ).queue()
            } else if (commandSender.permissionLevel.weight >= PermissionLevel.STAFF.weight) {
                channel.sendMessageEmbeds(helpEmbed(commandSender)).setActionRow(
                    Button.primary(commandSender.member.id + ":" + UUID.randomUUID(),"Staff commands"),
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Hypixel commands"),
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Practice commands"),
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Misc commands")
                ).queue()
            } else {
                channel.sendMessageEmbeds(helpEmbed(commandSender)).setActionRow(
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Hypixel commands"),
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Practice commands"),
                    Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Misc commands")
                ).queue()
            }
        } else if (args[0].equals("admin", ignoreCase = true) && commandSender.permissionLevel.weight >= PermissionLevel.ADMIN.weight) {
            channel.sendMessageEmbeds(adminCommandsPage1()).setActionRow(
                Button.secondary(commandSender.member.id + ":" + UUID.randomUUID() + ":ADMIN HELP","Next page")
            ).queue()
        } else if (args[0].equals("staff", ignoreCase = true) && commandSender.permissionLevel.weight >= PermissionLevel.STAFF.weight) {
            channel.sendMessageEmbeds(staffCommandsPage1()).setActionRow(
                Button.secondary(commandSender.member.id + ":" + UUID.randomUUID() + ":STAFF HELP","Next page")
            ).queue()
        } else if (args[0].equals("hypixel", ignoreCase = true)) {
            channel.sendMessageEmbeds(hypixelCommands()).queue()
        } else if (args[0].equals("practice", ignoreCase = true)) {
            channel.sendMessageEmbeds(practiceCommands()).queue()
        } else if (args[0].equals("misc", ignoreCase = true)) {
            channel.sendMessageEmbeds(miscCommandsPage1()).setActionRow(
                Button.secondary(commandSender.member.id + ":" + UUID.randomUUID() + ":MISC HELP","Next page")
            ).queue()
        } else {
            channel.sendMessageEmbeds(helpEmbed(commandSender)).setActionRow(
                Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Hypixel commands"),
                Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Practice commands"),
                Button.secondary(commandSender.member.id + ":" + UUID.randomUUID(),"Misc commands")
            ).queue()
        }
    }
}


