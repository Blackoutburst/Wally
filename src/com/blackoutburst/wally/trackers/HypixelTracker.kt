package com.blackoutburst.wally.trackers

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.emoji.Emoji
import java.lang.Thread.sleep

object HypixelTracker {

    fun run() {
        val thread = Thread {
            while (true) {
                for (user in Wally.users) {
                    checkPlayer(user)
                    sleep(500)
                }
            }
        }
        thread.start()
        thread.name = "Hypixel Tracker"
    }

    private fun checkPlayer(user: WallyUser) {
        val data = Hypixel.getPlayerStats(user.mainAccountUuid, true) ?: return
        val stats = HypixelStats(data)

        val localData = DatabaseUtils.getLocalData(user.mainAccountUuid, DatabaseUtils.DataType.HYPIXEL) ?: return
        val localStats = HypixelStats(localData)

        if (stats.finals > localStats.finals) {
            Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "tracker channel"))!!
                .sendMessageEmbeds(pbEmbed(stats, user.member.id, localStats.finals, stats.finals, "Finals")).queue { r ->
                    if (stats.finals - localStats.finals == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.HYPIXEL, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.HYPIXEL, user.member.id, user.mainAccountUuid)
        }

        if (stats.qualification > localStats.qualification) {
            Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "tracker channel"))!!
                .sendMessageEmbeds(pbEmbed(stats, user.member.id, localStats.qualification, stats.qualification, "Qualifiers")).queue{ r ->
                    if (stats.qualification - localStats.qualification == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.HYPIXEL, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.HYPIXEL, user.member.id, user.mainAccountUuid)
        }
    }

    private fun pbEmbed(stats :HypixelStats, discordId :String, oldScore :Int, newScore :Int, gameType :String) : MessageEmbed {
        val time = System.currentTimeMillis()
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        val wallyPfp = "https://www.blackoutburst.com/img/wally.png"

        val embed = EmbedBuilder()
            .setThumbnail("https://visage.surgeplay.com/full/512/${stats.uuid}?&time=$time")
            .setFooter("Discord ID: $discordId\nUUID: ${stats.uuid}")
            .setColor(Utils.getColorFromScore(newScore))
            .setAuthor("New $gameType PB !", wallyURL, wallyPfp)

        if (oldScore > 0) {
            embed.setTitle("${stats.name} improved ${Utils.getGenderPrefix(discordId)} **$gameType** Personal Best!")
            embed.addField("Old PB", "**$oldScore**",true)
            embed.addField("New PB", "**$newScore**",true)
            embed.addField("Increase","**${(newScore - oldScore)}**",true)
        } else {
            embed.setTitle("${stats.name} completed ${Utils.getGenderPrefix(discordId)} first game of **$gameType**!")
            embed.addField("They got", "**$newScore** points!",true)
        }

        return embed.build()
    }
}