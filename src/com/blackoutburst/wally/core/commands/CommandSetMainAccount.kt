package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.FilesUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import java.io.File

class CommandSetMainAccount : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 2) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "setMainAccount [discordID/Minecraft name] [Minecraft Name]")).queue()
            return
        }

        var discordID = getDiscordIDFromTwoArgs(guild, args)

        if (discordID == null) {
            Wally.logger.log(LogLevel.WARN, "Discord id is null")
            val playerUuid = Mojang.getPlayerUUID(args[0])
            if (playerUuid == null) {
                Wally.logger.log(LogLevel.WARN, "Player uuid is null")
                message.reply(ConfigManager.loadMessage( "not an user")).queue()
                return
            }

            val wallyUser = DatabaseUtils.getWallyUserFromUUID(playerUuid)
            if (wallyUser == null) {
                Wally.logger.log(LogLevel.WARN, "Wally user is missing")
                message.reply(ConfigManager.loadMessage( "not linked")
                    .replace("%user%", args[0])).queue()
                return
            }

            discordID = wallyUser.member.id
        }
        val option = DatabaseUtils.getWallyUserData(discordID)
        if (option == null) {
            Wally.logger.log(LogLevel.WARN, "User data are missing")
            message.reply(ConfigManager.loadMessage( "unlink not link")).queue()
            return
        }

        val playerName = args[1]
        val player = Mojang.getPlayerUUID(playerName)
        if (player == null) {
            Wally.logger.log(LogLevel.WARN, "Player is null")
            message.reply(ConfigManager.loadMessage( "not in mojang").replace("%name%", playerName)).queue()
            return
        }
        val user = DatabaseUtils.getWallyUserFromUUID(option.mainAccountUUID)

        val folder = File("database/linked users/$discordID/accounts/")
        val entries = folder.list()

        if (!entries!!.contains(player)) {
            message.reply(ConfigManager.loadMessage( "not user alt")
                .replace("%name%", playerName)
                .replace("%user%", user!!.member.effectiveName)
            ).queue()
            return
        }

        FilesUtils.excludeFromLb(option.mainAccountUUID)

        option.mainAccountUUID = player

        FilesUtils.removeExclusion(option.mainAccountUUID)

        DatabaseUtils.generateWallyProfile(option, "database/linked users/$discordID/data.json")
        Wally.loadUser()

        message.reply(ConfigManager.loadMessage( "main account updated")
            .replace("%user%", user!!.member.effectiveName)
            .replace("%name%", playerName)).queue()

        val hypixelData = Hypixel.getPlayerStats(option.mainAccountUUID)
        if (hypixelData != null) {
            Wally.logger.log(LogLevel.WARN, "Hypixel data are missing")
            DatabaseUtils.updateLeaderboardStats(hypixelData, DatabaseUtils.DataType.HYPIXEL, option.mainAccountUUID)
        }

        val practiceData = PracticeServer.getPlayerStats(option.mainAccountUUID)
        if (practiceData != null) {
            Wally.logger.log(LogLevel.WARN, "Practice data are missing")
            DatabaseUtils.updateLeaderboardStats(practiceData, DatabaseUtils.DataType.PRACTICE, option.mainAccountUUID)
        }
    }
}