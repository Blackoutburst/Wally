package com.blackoutburst.wally.utils

import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.core.UserOption
import com.blackoutburst.wally.core.commands.PermissionLevel
import com.blackoutburst.wally.core.commands.DisplayPreference
import com.blackoutburst.wally.core.database.DatabaseManager
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.commands.CommandManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import java.util.Locale

object CommandUtils {

    /**
     * Create user or alt account data
     * manage error by steps
     *
     * @param isAltAccount decide if the account is an alt account or a main
     * @param guild the server running the command
     * @param args the command args
     * @param message the command message
     * @param discordID the user discordID
     * @param channel the message channel
     * @param commandSender the command sender
     */
    fun createUserData(isAltAccount: Boolean, guild: Guild, args: Array<String>, message: Message, discordID: String, channel: MessageChannel, commandSender: WallyUser) {
        val playerName = getNonDiscordIDArgFromTwoArgs(guild, args)
        if (playerName == null) {
            message.reply(ConfigManager.loadMessage( "could not get name")).queue()
            return
        }

        val playerUUID = Mojang.getPlayerUUID(playerName)
        if (playerUUID == null) {
            message.reply(ConfigManager.loadMessage( "not in mojang").replace("%name%", playerName)).queue()
            return
        }

        val hypixelData = Hypixel.getPlayerStats(playerUUID)
        if (hypixelData == null) {
            message.reply(ConfigManager.loadMessage( "not in hypixel").replace("%name%", playerName)).queue()
            return
        }

        var practiceData = PracticeServer.getPlayerStats(playerUUID)
        if (practiceData == null) {
            channel.sendMessage(ConfigManager.loadMessage( "could not load practice data").replace("%name%", playerName)).queue()
            practiceData = ""
        }

        val wallyProfile = UserOption(PermissionLevel.MEMBER, DisplayPreference.CANVAS, playerUUID, guild.id)
        if (isAltAccount) {
            if (!DatabaseManager.createAltData(discordID, playerUUID, hypixelData, practiceData)) {
                MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "add alt error"))
                return
            }
            FilesUtils.excludeFromLb(playerUUID)
            channel.sendMessage(ConfigManager.loadMessage( "add alt success").replace("%name%", playerName)
                .replace("%user%", "<@$discordID>")).queue()
            Wally.logger.log(LogLevel.INFO, "$playerName was added as alt to $discordID")
        } else {
            if (!DatabaseManager.createUserData(discordID, playerUUID, hypixelData, practiceData, wallyProfile)) {
                MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.loadMessage( "link error"))
                return
            }
            channel.sendMessage(ConfigManager.loadMessage( "link success").replace("%name%", playerName)).queue()
            Wally.logger.log(LogLevel.INFO, "$playerName was linked to $discordID")
        }
    }

    /**
     * Return the discord ID from the command args
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the discord id
     */
    fun getDiscordIDFromTwoArgs(guild: Guild, args: Array<String>): String? {
        if (guild.getMemberById(args[0]) != null) return args[0]
        if (guild.getMemberById(args[1]) != null) return args[1]
        return null
    }

    /**
     * Get the args which isn't a discord id
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the second arg
     */
    fun getNonDiscordIDArgFromTwoArgs(guild: Guild, args: Array<String>): String? {
        if (guild.getMemberById(args[0]) != null) return args[1]
        if (guild.getMemberById(args[1]) != null) return args[0]
        return null
    }

    /**
     * Get command arguments
     *
     * @param message the message as string
     * @return the command arguments
     */
    fun getCommandArgs(message: String): Array<String> {
        val tmp = message.trim { it <= ' ' }.split(" ").toTypedArray()

        return tmp.copyOfRange(1, tmp.size)
    }

    /**
     * Get the command name
     *
     * @param message the message as string
     * @return the command name
     */
    fun getCommandName(message: String): String {
        val tmp = message.trim { it <= ' ' }.split(" ").toTypedArray()

        return tmp[0].substring(CommandManager.prefix.length).lowercase(Locale.getDefault())
    }
}