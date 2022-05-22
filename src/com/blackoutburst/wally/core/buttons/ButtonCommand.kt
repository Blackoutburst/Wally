package com.blackoutburst.wally.core.buttons

import com.blackoutburst.wally.utils.MessageUtils.getMessageFromHistory
import com.blackoutburst.wally.utils.CommandUtils.getCommandArgs
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.commands.CommandManager

class ButtonCommand : ButtonExecutable() {

    override fun onClick(buttonUser: WallyUser, event: ButtonInteractionEvent) {
        val messageChannel = event.messageChannel
        val messageID = event.button.id!!.split(":").toTypedArray()[1]
        val message = getMessageFromHistory(messageChannel, messageID, 5)

        if (message == null) {
            messageChannel.sendMessage("<@" + event.member!!.id + ">" + ConfigManager.loadValue("replies", "message deleted")).queue()
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
        if (guild == null) return
        if (member == null) return

        CommandManager.runCommand(buttonUser, commandName, commandArgs, guild, messageChannel, message)
        event.message.delete().queue()
    }
}