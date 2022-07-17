package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserFromUUID
import com.blackoutburst.wally.utils.MessageUtils.sendHypixelCompare
import com.blackoutburst.wally.utils.Utils.getHighestScore
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import java.nio.file.Files
import java.nio.file.Paths

class SelectMenuCompare : SelectMenuExecutable() {

    override fun onChange(menuUser: WallyUser, event: SelectMenuInteractionEvent) {
        val uuid2 = event.selectedOptions[0].value.split(":").last()
        if (event.selectedOptions[0].label == "Combined stats") {
            val uuid = event.selectMenu.options.map { it.value.split(":").first() }.filter { !it.contains("all") }
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

            val data2 = DatabaseUtils.getLocalData(uuid2, DatabaseUtils.DataType.HYPIXEL)
            sendHypixelCompare(menuUser, event.message, combinedStats, HypixelStats(data2!!), true)
        } else {
            val uuid = event.selectedOptions[0].value.split(":").first()
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

            val data2 = DatabaseUtils.getLocalData(uuid2, DatabaseUtils.DataType.HYPIXEL)
            sendHypixelCompare(menuUser, event.message, HypixelStats(data), HypixelStats(data2!!), true)
        }
    }
}
