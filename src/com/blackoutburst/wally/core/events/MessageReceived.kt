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
                        event.message.reply(ConfigManager.loadMessage("you are not linked")).queue()
                        return
                    }
                    val user = DatabaseUtils.getWallyUserFromUUID(userData.mainAccountUUID)
                    if (user == null) {
                        event.message.reply(ConfigManager.loadMessage("you are not linked")).queue()
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
                        event.message.reply(ConfigManager.loadMessage("you are not linked")).queue()
                        return
                    }
                    val user = DatabaseUtils.getWallyUserFromUUID(userData.mainAccountUUID)
                    if (user == null) {
                        event.message.reply(ConfigManager.loadMessage("you are not linked")).queue()
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

    fun ez(event: MessageReceivedEvent) {
        val messages = listOf(
        "Wait... This isn't what I typed!",
        "Anyone else really like Rick Astley?",
        "Hey helper, how play game?",
        "Sometimes I sing soppy, love songs in the car.",
        "I like long walks on the beach and playing Hypixel",
        "Please go easy on me, this is my first game!",
        "You're a great person! Do you want to play some Hypixel games with me?",
        "In my free time I like to watch cat videos on Youtube",
        "When I saw the witch with the potion, I knew there was trouble brewing.",
        "If the Minecraft world is infinite, how is the sun spinning around it?",
        "Hello everyone! I am an innocent player who loves everything Hypixel.",
        "Plz give me doggo memes!",
        "I heard you like Minecraft, so I built a computer in Minecraft in your Minecraft so you can Minecraft while you Minecraft",
        "Why can't the Ender Dragon read a book? Because he always starts at the End.",
        "Maybe we can have a rematch?",
        "I sometimes try to say bad things then this happens",
        "Behold, the great and powerful, my magnificent and almighty nemisis!",
        "Doin a bamboozle fren.",
        "Your clicks per second are godly.",
        "What happens if I add chocolate milk to macaroni and cheese?",
        "Can you paint with all the colors of the wind",
        "Blue is greener than purple for sure",
        "I had something to say, then I forgot it.",
        "When nothing is right, go left.",
        "I need help, teach me how to play!",
        "Your personality shines brighter than the sun.",
        "You are very good at the game friend.",
        "I like pineapple on my pizza",
        "I like pasta, do you prefer nachos?",
        "I like Minecraft pvp but you are truly better than me!",
        "I have really enjoyed playing with you! <3",
        "ILY <3",
        "Pineapple doesn't go on pizza!",
        "Lets be friends instead of fighting okay?"
        )

        if (event.message.contentRaw.contains("ez", true)) {
            event.channel.sendTyping().queue()
            event.channel.sendMessage(messages.random()).queue()
        }
    }

    fun run(event: MessageReceivedEvent) {
        wallyPing(event)
        ez(event)
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