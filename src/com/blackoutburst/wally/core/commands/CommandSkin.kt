package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.utils.DatabaseUtils.isLinked
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserData
import com.blackoutburst.wally.core.request.Mojang.getPlayerName
import com.blackoutburst.wally.utils.EmbedUtils.generateSkin
import com.blackoutburst.wally.utils.MessageUtils.createAccountMenu
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandSkin : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        val discordID = commandSender.member.id
        val uuid: String? = if (args.isNotEmpty()) {
            getPlayerUUID(args[0])
        } else if (isLinked(discordID)) {
            val data = getWallyUserData(discordID)
            data!!.mainAccountUUID
        } else {
            message.reply(ConfigManager.loadMessage( "invalid command usage").replace("%usage%", CommandManager.prefix + "skin [Minecraft name]")).queue()
            return
        }

        if (uuid == null) {
            message.reply(ConfigManager.loadMessage( "unknown player")).queue()
            return
        }

        val playerName = getPlayerName(uuid)
        val embed = generateSkin(uuid, playerName!!)
        val menu = createAccountMenu(commandSender, uuid, "skin")

        menu?.let {
            message.replyEmbeds(embed.build()).setActionRow(it).queue()
        } ?: run {
            message.replyEmbeds(embed.build()).queue()
        }
    }
}