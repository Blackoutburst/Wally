package com.blackoutburst.wally.threads

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.EmbedUtils.pbEmbed
import net.dv8tion.jda.api.entities.emoji.Emoji
import java.lang.Thread.sleep

object PracticeTracker {

    fun run() {
        val thread = Thread {
            while (true) {
                for (i in 0..Wally.users.size) {
                    val user = Wally.users[i]
                    checkPlayer(user)
                    sleep(500)
                }
            }
        }
        thread.start()
        thread.name = "Practice Tracker"
    }

    private fun checkPlayer(user: WallyUser) {
        val data = PracticeServer.getPlayerStats(user.mainAccountUuid, true) ?: return
        val stats = PracticeStats(data)

        val localData = DatabaseUtils.getLocalData(user.mainAccountUuid, DatabaseUtils.DataType.PRACTICE) ?: return
        val localStats = PracticeStats(localData)

        if (stats.finals > localStats.finals) {
            Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "practice tracker channel"))!!
                .sendMessageEmbeds(pbEmbed(stats, user, localStats.finals, stats.finals, "Finals")).queue { r ->
                    if (stats.finals - localStats.finals == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.PRACTICE, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.PRACTICE, user.member.id, user.mainAccountUuid)
        }

        if (stats.qualification > localStats.qualification) {
            Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "practice tracker channel"))!!
                .sendMessageEmbeds(pbEmbed(stats, user, localStats.qualification, stats.qualification, "Qualifiers")).queue{ r ->
                    if (stats.qualification - localStats.qualification == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.PRACTICE, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.PRACTICE, user.member.id, user.mainAccountUuid)
        }

        if (stats.lobby > localStats.lobby) {
            Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "practice tracker channel"))!!
                .sendMessageEmbeds(pbEmbed(stats, user, localStats.lobby, stats.lobby, "Lobby")).queue{ r ->
                    if (stats.lobby - localStats.lobby == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.PRACTICE, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.PRACTICE, user.member.id, user.mainAccountUuid)
        }

        if (stats.wideQualification > localStats.wideQualification) {
            Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "practice tracker channel"))!!
                .sendMessageEmbeds(pbEmbed(stats, user, localStats.wideQualification, stats.wideQualification, "Wide Qualifiers")).queue{ r ->
                    if (stats.wideQualification - localStats.wideQualification == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.PRACTICE, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.PRACTICE, user.member.id, user.mainAccountUuid)
        }

        if (stats.wideFinals > localStats.wideFinals) {
            Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "practice tracker channel"))!!
                .sendMessageEmbeds(pbEmbed(stats, user, localStats.wideFinals, stats.wideFinals, "Wide Finals")).queue{ r ->
                    if (stats.wideFinals - localStats.wideFinals == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.PRACTICE, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.PRACTICE, user.member.id, user.mainAccountUuid)
        }
    }
}