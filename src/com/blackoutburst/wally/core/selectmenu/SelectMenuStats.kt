package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserFromUUID
import com.blackoutburst.wally.utils.MessageUtils.sendHypixelStats
import com.blackoutburst.wally.core.WallyUser
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import java.nio.file.Paths
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.utils.Utils.getHighestScore
import java.nio.file.Files
import java.util.ArrayList

class SelectMenuStats : SelectMenuExecutable() {

    override fun onChange(menuUser: WallyUser, event: SelectMenuInteractionEvent) {
        if (event.selectedOptions[0].label == "Combined stats") {
            val uuid = event.selectedOptions[0].value.split(":").dropLast(1)
            val wallyUser = getWallyUserFromUUID(uuid[0])
            val memberId = wallyUser!!.member.id

            val stats: MutableList<HypixelStats> = ArrayList()

            uuid.forEach { u ->
                val filePath = Paths.get("database/linked users/$memberId/accounts/$u/hypixel.json")
                try {
                    val data = String(Files.readAllBytes(filePath))
                    stats.add(HypixelStats(data))
                } catch (e: Exception) {
                    Wally.logger.logStackTrace(e)
                }
            }

            var wins = 0
            stats.forEach { s -> wins += s.wins }
            var walls = 0
            stats.forEach { s -> walls += s.walls }
            val qualification = getHighestScore(stats.map { s-> s.qualification }.toIntArray())
            val finals = getHighestScore(stats.map { s-> s.finals }.toIntArray())
            val total = qualification + finals
            val totalAPI = getHighestScore(stats.map { s-> s.totalAPI }.toIntArray())
            var challenges = 0
            stats.forEach { s -> challenges += s.challenges }

            val combinedStats = HypixelStats(wallyUser.mainAccountUuid, "${wallyUser.member.effectiveName}#${wallyUser.member.user.discriminator}", wins, walls, qualification, finals, total, totalAPI, challenges)

            event.deferEdit().queue()

            sendHypixelStats(menuUser, event.message, combinedStats, uuid[0], true)
        } else {
            val uuid = event.selectedOptions[0].value
            val wallyUser = getWallyUserFromUUID(uuid)
            val memberId = wallyUser!!.member.id
            val filePath = Paths.get("database/linked users/$memberId/accounts/$uuid/hypixel.json")
            var data = ""
            try {
                data = String(Files.readAllBytes(filePath))
            } catch (e: Exception) {
                Wally.logger.logStackTrace(e)
            }

            event.deferEdit().queue()

            sendHypixelStats(menuUser, event.message, HypixelStats(data), uuid, true)
        }
    }
}