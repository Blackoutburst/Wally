package com.blackoutburst.wally.core.commands

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.utils.CommandUtils
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.EmbedUtils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.emoji.Emoji

class CommandFakePracticePb : CommandExecutable() {

    override fun onCommand(commandSender: WallyUser, commandName: String, args: Array<String>, guild: Guild, channel: MessageChannel, message: Message) {
        if (args.size < 4) {
            message.reply(ConfigManager.loadMessage( "invalid command usage")
                .replace("%usage%", CommandManager.prefix + "fakePracticePB [discordID/Minecraft name] [GameType] [Old score] [New score]")).queue()
            return
        }

        var discordID = CommandUtils.getDiscordIDFromTwoArgs(guild, args)

        if (discordID == null) {
            val playerUuid = Mojang.getPlayerUUID(args[0])
            if (playerUuid == null) {
                message.reply(ConfigManager.loadMessage( "not an user")).queue()
                return
            }

            val wallyUser = DatabaseUtils.getWallyUserFromUUID(playerUuid)
            if (wallyUser == null) {
                message.reply(ConfigManager.loadMessage( "not linked")
                    .replace("%user%", args[0])).queue()
                return
            }

            discordID = wallyUser.member.id
        }

        val options = DatabaseUtils.getWallyUserData(discordID)
        if (options == null) {
            message.reply(ConfigManager.loadMessage( "not linked")
                .replace("%user%", args[0])).queue()
            return
        }
        val user = DatabaseUtils.getWallyUserFromUUID(options.mainAccountUUID)
        val data = DatabaseUtils.getLocalData(options.mainAccountUUID, DatabaseUtils.DataType.PRACTICE)
        if (data == null) return

        val stats = PracticeStats(data)
        val oldScore: Int = try {
            args[2].toInt()
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            message.reply(ConfigManager.loadMessage( "not a number")
                .replace("%num%", args[2])).queue()
            return
        }
        val newScore: Int = try {
            args[3].toInt()
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            message.reply(ConfigManager.loadMessage( "not a number")
                .replace("%num%", args[3])).queue()
            return
        }

        Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "practice tracker channel"))!!
            .sendMessageEmbeds(
                EmbedUtils.pbEmbed(stats, user!!, oldScore, newScore, args[1])
            ).queue{ r ->
                if (newScore - oldScore == 1) {
                    r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                }
            }
        channel.sendMessage(ConfigManager.loadMessage("fakePB").replace("%name%", stats.name)).queue()
    }
}


