package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.UserOption
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager
import com.blackoutburst.wally.core.commands.DisplayPreference
import com.blackoutburst.wally.core.commands.PermissionLevel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.database.DatabaseManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.core.request.Visage
import com.blackoutburst.wally.core.statsobject.HypixelStats
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.util.*

object CommandUtils {

    fun getSubtitle(message: Message): String {
        val tmp = message.contentRaw.trim { it <= ' ' }.split(" ").toTypedArray()
        val words = tmp.copyOfRange(2, tmp.size)
        val builder = StringBuilder()

        words.forEach { w: String? -> builder.append(w).append(' ') }
        return builder.toString()
    }

    fun concatArgs(message: Message): String {
        val tmp = message.contentRaw.trim { it <= ' ' }.split(" ").toTypedArray()
        val words = tmp.copyOfRange(1, tmp.size)
        val builder = StringBuilder()

        words.forEach { w: String? -> builder.append(w).append(' ') }
        return builder.toString()
    }

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
            val member = Wally.hitwDiscord!!.getMemberById(discordID)
            if (member != null)
                channel.sendMessageEmbeds(EmbedUtils.welcomeEmbed(HypixelStats(hypixelData), WallyUser(member, wallyProfile)))
        }
        Visage.getPlayerHead(playerUUID)
    }

    fun getDiscordIDFromArg(guild: Guild, arg :String): String? {
        return try {
            if (guild.getMemberById(arg) != null)
                arg
            else
                null
        } catch (ignored :Exception) {
            null
        }
    }

    fun getDiscordIDFromTwoArgs(guild: Guild, args: Array<String>): String? {
        try { if (guild.getMemberById(args[0]) != null) return args[0] } catch (ignored :Exception) {}
        try { if (guild.getMemberById(args[1]) != null) return args[1] } catch (ignored :Exception) {}
        return null
    }

    fun getNonDiscordIDArgFromTwoArgs(guild: Guild, args: Array<String>): String? {
        try {  if (guild.getMemberById(args[0]) != null) return args[1] } catch (ignored :Exception) {}
        try {  if (guild.getMemberById(args[1]) != null) return args[0] } catch (ignored :Exception) {}
        return null
    }

    fun getCommandArgs(message: String): Array<String> {
        val tmp = message.trim { it <= ' ' }.split(" ").toTypedArray()

        return tmp.copyOfRange(1, tmp.size)
    }

    fun getCommandName(message: String): String {
        val tmp = message.trim { it <= ' ' }.split(" ").toTypedArray()

        return tmp[0].substring(CommandManager.prefix.length).lowercase(Locale.getDefault())
    }
}