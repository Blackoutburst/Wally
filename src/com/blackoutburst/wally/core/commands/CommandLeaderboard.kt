package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.utils.MessageUtils.sendLeaderboard
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandLeaderboard : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        var page = 1
        if (args.isNotEmpty()) {
            page = try {
                args[0].toInt()
            } catch (e: NumberFormatException) {
                Wally.logger.logStackTrace(e.stackTrace)
                message.reply(ConfigManager.loadMessage( "invalid page").replace("%page%", args[0])).queue()
                return
            }
        }

        if (page < 1) {
            message.reply(ConfigManager.loadMessage( "invalid page").replace("%page%", args[0])).queue()
            return
        }
        sendLeaderboard(commandSender, message, LeaderboardManager.winsLeaderboardHypixel, page, false)
    }
}