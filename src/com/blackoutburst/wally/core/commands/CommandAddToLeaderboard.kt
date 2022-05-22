package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.commands.CommandManager.prefix
import com.blackoutburst.wally.core.request.Mojang.getPlayerUUID
import com.blackoutburst.wally.utils.FilesUtils.removeExclusion
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.entities.MessageChannel
import com.blackoutburst.wally.core.config.ConfigManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message

class CommandAddToLeaderboard : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size != 1) {
            message.reply(ConfigManager.loadValue("replies", "invalid command usage")
                .replace("%usage%", prefix + "addToLeaderboard [Minecraft Name]")).queue()
            return
        }

        val playerUUID = getPlayerUUID(args[0])
        if (playerUUID == null) {
            message.reply(ConfigManager.loadValue("replies", "not in mojang").replace("%name%", args[0])).queue()
            return
        }

        removeExclusion(playerUUID)
        channel.sendMessage(ConfigManager.loadValue("replies", "remove exclude success").replace("%name%", args[0])).queue()
    }
}