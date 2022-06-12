package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.utils.CommandUtils.getDiscordIDFromTwoArgs
import com.blackoutburst.wally.utils.CommandUtils.getNonDiscordIDArgFromTwoArgs
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
                .replace("%usage%", prefix + "setMainAccount [discordID] [Minecraft Name]")).queue()
            return
        }

        val discordID = getDiscordIDFromTwoArgs(guild, args)
        if (discordID == null) {
            message.reply(ConfigManager.loadMessage( "not an user")).queue()
            return
        }
        val option = DatabaseUtils.getWallyUserData(discordID)
        if (option == null) {
            message.reply(ConfigManager.loadMessage( "unlink not link")).queue()
            return
        }

        val playerName = getNonDiscordIDArgFromTwoArgs(guild, args)!!
        val player = Mojang.getPlayerUUID(playerName)
        if (player == null) {
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
        if (hypixelData != null)
            DatabaseUtils.updateLeaderboardStats(hypixelData, DatabaseUtils.DataType.HYPIXEL, option.mainAccountUUID)

        val practiceData = PracticeServer.getPlayerStats(option.mainAccountUUID)
        if (practiceData != null)
            DatabaseUtils.updateLeaderboardStats(practiceData, DatabaseUtils.DataType.PRACTICE, option.mainAccountUUID)
    }
}