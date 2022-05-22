package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.utils.FilesUtils.excludeFromLb
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandExcludeFromLeaderboard : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", prefix + "excludeFromLeaderboard [Minecraft Name]")).queue()
            return
        }

        val playerUUID = getPlayerUUID(args[0])
        if (playerUUID == null) {
            message.reply(ConfigManager.loadMessage( "not in mojang").replace("%name%", args[0])).queue()
            return
        }

        excludeFromLb(playerUUID)
        channel.sendMessage(ConfigManager.loadMessage( "exclude success").replace("%name%", args[0])).queue()
    }
}