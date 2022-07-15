package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.utils.CanvasUtils
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.MessageUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.awt.Color
import java.io.File

class CommandSetFontColor : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "setFontColor [color]")).queue()
            return
        }

        val discordID = commandSender.member.id
        if (!DatabaseUtils.isLinked(discordID)) {
            Wally.logger.log(LogLevel.WARN, "User is not linked")
            message.reply(ConfigManager.loadMessage( "you are not linked")).queue()
            return
        }

        val option = DatabaseUtils.getWallyUserData(discordID)
        if (option == null) {
            Wally.logger.log(LogLevel.WARN, "User data are missing")
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage("user data error"))
            return
        }

        val color = if (args[0].startsWith("#")) args[0] else "#${args[0]}"


        try { Color.decode(color) } catch (e :Exception) {
            Wally.logger.logStackTrace(e)
            message.reply(ConfigManager.loadMessage( "not a valid color").replace("%color%", color)).queue()
            return
        }

        option.fontColor = color

        DatabaseUtils.generateWallyProfile(option, "database/linked users/$discordID/data.json")
        Wally.loadUser()

        val updatedUser = DatabaseUtils.getWallyUserFromUUID(option.mainAccountUUID)

        CanvasUtils.createFontPreviewCanvas(updatedUser!!)
        message.reply(ConfigManager.loadMessage( "font color update")).addFile(File("font_preview.png")).queue()
    }
}