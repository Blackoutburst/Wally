package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.CanvasUtils
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.MessageUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandSetFontSize : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "setFontSize [size]")).queue()
            return
        }

        val discordID = commandSender.member.id
        if (!DatabaseUtils.isLinked(discordID)) {
            message.reply(ConfigManager.loadMessage( "you are not linked")).queue()
            return
        }

        val option = DatabaseUtils.getWallyUserData(discordID)
        if (option == null) {
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage("user data error"))
            return
        }

        val size = args[0]


        try { args[0].toFloat() } catch (ignored :Exception) {
            message.reply(ConfigManager.loadMessage( "not a valid size").replace("%size%", size)).queue()
            return
        }

        option.fontSize = size.toFloat()

        DatabaseUtils.generateWallyProfile(option, "database/linked users/$discordID/data.json")
        Wally.loadUser()

        val updatedUser = DatabaseUtils.getWallyUserFromUUID(option.mainAccountUUID)

        CanvasUtils.createFontPreviewCanvas(updatedUser!!)
        message.reply(ConfigManager.loadMessage( "font size update")).addFile(File("font_preview.png")).queue()
    }
}