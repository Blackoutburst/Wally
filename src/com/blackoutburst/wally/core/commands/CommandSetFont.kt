package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.CanvasUtils
import com.blackoutburst.wally.utils.DatabaseUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandSetFont : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (message.attachments.size == 0) {
            message.reply(ConfigManager.loadMessage( "missing font attachment")).queue()
            return
        }

        val discordID = commandSender.member.id
        if (!DatabaseUtils.isLinked(discordID)) {
            message.reply(ConfigManager.loadMessage( "you are not linked")).queue()
            return
        }

        val file = message.attachments[0]
        if (file.fileExtension == "ttf" || file.fileExtension == "otf") {
            file.downloadToFile(File("database/linked users/$discordID/resources/font.ttf")).join()
            CanvasUtils.createFontPreviewCanvas(commandSender)
            message.reply(ConfigManager.loadMessage( "font update")).addFile(File("font_preview.png")).queue()
        } else {
            message.reply(ConfigManager.loadMessage( "not a font file")).queue()
        }
    }
}