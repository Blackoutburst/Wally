package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser.Companion.getUser
import com.blackoutburst.wally.core.commands.CommandManager
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.threads.HypixelTracker
import com.blackoutburst.wally.core.threads.PracticeTracker
import com.blackoutburst.wally.utils.CommandUtils.getCommandArgs
import com.blackoutburst.wally.utils.CommandUtils.getCommandName
import com.blackoutburst.wally.utils.DatabaseUtils
import net.dv8tion.jda.api.entities.MessageType
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

object MessageReceived {

    private fun wallyPing(event: MessageReceivedEvent) {
        if (event.message.type == MessageType.INLINE_REPLY) return
        if (event.member == null) return
        if (event.channel.id == ConfigManager.loadValue("channels", "tracker channel")) {
            val members = event.message.mentions.members
            for (member in members) {
                if (member.id == Wally.jda?.selfUser?.id) {
                    event.channel.sendTyping().queue()
                    val userData = DatabaseUtils.getWallyUserData(event.member!!.id)
                    if (userData == null) {
                        event.message.reply(ConfigManager.loadMessage("sorry not linked")).queue()
                        return
                    }
                    val user = DatabaseUtils.getWallyUserFromUUID(userData.mainAccountUUID)
                    if (user == null) {
                        event.message.reply(ConfigManager.loadMessage("sorry not linked")).queue()
                        return
                    }
                    if (!HypixelTracker.checkPlayer(user)) {
                        if (event.member!!.id == "859202954631053333")
                            event.message.reply(ConfigManager.loadMessage("esk")).queue()
                        else
                            event.message.reply(ConfigManager.loadMessage("nothing to show")).queue()
                    }
                }
            }
        }

        if (event.channel.id == ConfigManager.loadValue("channels", "practice tracker channel")) {
            val members = event.message.mentions.members
            for (member in members) {
                if (member.id == Wally.jda?.selfUser?.id) {
                    event.channel.sendTyping().queue()
                    val userData = DatabaseUtils.getWallyUserData(event.member!!.id)
                    if (userData == null) {
                        event.message.reply(ConfigManager.loadMessage("sorry not linked")).queue()
                        return
                    }
                    val user = DatabaseUtils.getWallyUserFromUUID(userData.mainAccountUUID)
                    if (user == null) {
                        event.message.reply(ConfigManager.loadMessage("sorry not linked")).queue()
                        return
                    }
                    if (!PracticeTracker.checkPlayer(user)) {
                        if (event.member!!.id == "859202954631053333")
                            event.message.reply(ConfigManager.loadMessage("esk")).queue()
                        else
                            event.message.reply(ConfigManager.loadMessage("nothing to show")).queue()
                    }
                }
            }
        }

    }

    fun run(event: MessageReceivedEvent) {
        wallyPing(event)
        if (!event.message.contentRaw.startsWith(CommandManager.prefix)) return
        if (!Wally.isReady) {
            event.message.reply(ConfigManager.loadMessage( "still loading")).queue()
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