package com.blackoutburst.wally.threads

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.core.roles.RolesManager
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.EmbedUtils.pbEmbed
import com.blackoutburst.wally.utils.Utils
import net.dv8tion.jda.api.entities.emoji.Emoji
import java.lang.Thread.sleep

object HypixelTracker {

    fun run() {
        val thread = Thread {
            while (true) {
                for (i in 0 until Wally.users.size) {
                    val user = Wally.users[i]
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
                .sendMessageEmbeds(pbEmbed(stats, user, localStats.finals, stats.finals, "Finals")).queue { r ->
                    if (stats.finals - localStats.finals == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.HYPIXEL, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.HYPIXEL, user.member.id, user.mainAccountUuid)
            RolesManager.addClubRole(Utils.getHighestScore(stats.scores), user.member)
        }

        if (stats.qualification > localStats.qualification) {
            Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "tracker channel"))!!
                .sendMessageEmbeds(pbEmbed(stats, user, localStats.qualification, stats.qualification, "Qualifiers")).queue{ r ->
                    if (stats.qualification - localStats.qualification == 1) {
                        r.addReaction(Emoji.fromFormatted("weTakeThose:839985386012147783")).queue()
                    }
                }

            DatabaseUtils.updateLeaderboardStats(data, DatabaseUtils.DataType.HYPIXEL, user.mainAccountUuid)
            DatabaseUtils.updateLinkedPlayerStats(data, DatabaseUtils.DataType.HYPIXEL, user.member.id, user.mainAccountUuid)
            RolesManager.addClubRole(Utils.getHighestScore(stats.scores), user.member)
        }

        RolesManager.cleanLifeTimeRoles(user.member, stats)
        RolesManager.addLifeTimeRoles(user.member, stats)
    }
}