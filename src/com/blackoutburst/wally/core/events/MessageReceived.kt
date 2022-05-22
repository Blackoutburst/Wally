package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.utils.CommandUtils.getCommandName
import com.blackoutburst.wally.utils.CommandUtils.getCommandArgs
import com.blackoutburst.wally.core.WallyUser.Companion.getUser
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.blackoutburst.wally.core.commands.CommandManager
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager

object MessageReceived {
    /**
     * Read incoming messages and turn them into potential commands
     *
     * @param event The message event
     */
    fun run(event: MessageReceivedEvent) {
        if (!event.message.contentRaw.startsWith(CommandManager.prefix)) return
        if (!Wally.isReady) {
            event.message.reply(ConfigManager.loadValue("replies", "still loading")).queue()
            return
        }

        val messageString = event.message.contentRaw
        val commandName = getCommandName(messageString)
        val commandArgs = getCommandArgs(messageString)
        val guild = event.guild
        val messageChannel = event.channel
        val message = event.message
        val member = event.member ?: return
        val commandSender = getUser(member)
        CommandManager.runCommand(commandSender, commandName, commandArgs, guild, messageChannel, message)
    }
}