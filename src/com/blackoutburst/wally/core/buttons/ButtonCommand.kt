package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.utils.APIUtils
import com.blackoutburst.wally.utils.CommandUtils.getCommandArgs
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent

class ButtonCommand : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val messageChannel = event.messageChannel
        val messageID = event.button.id!!.split(":").toTypedArray()[1]
        val message = APIUtils.getMessageFromHistory(messageChannel, messageID, 5)

        if (message == null) {
            Wally.logger.log(LogLevel.WARN,"The message no longer exist")
            messageChannel.sendMessage("<@" + event.member!!.id + ">" + ConfigManager.loadMessage( "message deleted")).queue()
            return
        }

        var label = event.button.label
        if (label.startsWith("Yes run")) {
            label = label.split(" ").toTypedArray()[2]
        }

        val messageString = message.contentRaw
        val commandName = label
        val commandArgs = getCommandArgs(messageString)
        val guild = event.guild
        val member = event.member
        if (guild == null) {
            Wally.logger.log(LogLevel.WARN,"The guild is null!")
            return
        }
        if (member == null)  {
            Wally.logger.log(LogLevel.WARN,"The member is null!")
            return
        }

        CommandManager.runCommand(buttonUser, commandName, commandArgs, guild, messageChannel, message)
        event.message.delete().queue()
    }
}