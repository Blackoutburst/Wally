package com.blackoutburst.wally.core.selectmenu

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.utils.DatabaseUtils
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserFromUUID
import com.blackoutburst.wally.utils.MessageUtils.sendPracticeCompare
import com.blackoutburst.wally.utils.Utils
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent
import java.nio.file.Files
import java.nio.file.Paths

class SelectMenuPracticeCompare : SelectMenuExecutable() {

    override fun onChange(menuUser: WallyUser, event: SelectMenuInteractionEvent) {
        val uuid2 = event.selectedOptions[0].value.split(":").last()
        if (event.selectedOptions[0].label == "Combined stats") {
            val uuid = event.selectMenu.options.map { it.value.split(":").first() }.filter { !it.contains("all") }
            val wallyUser = getWallyUserFromUUID(uuid[0])
            val memberId = wallyUser!!.member.id
            val stats: MutableList<PracticeStats> = ArrayList()

            uuid.forEach { u ->
                val filePath = Paths.get("database/linked users/$memberId/accounts/$u/practice.json")
                try {
                    val data = String(Files.readAllBytes(filePath))
                    stats.add(PracticeStats(data))
                } catch (e: Exception) {
                    Wally.logger.logStackTrace(e)
                }
            }

            val qualification = Utils.getHighestScore(stats.map { s -> s.qualification }.toIntArray())
            val finals = Utils.getHighestScore(stats.map { s -> s.finals }.toIntArray())
            val lobby = Utils.getHighestScore(stats.map { s -> s.lobby }.toIntArray())
            val wideQualification = Utils.getHighestScore(stats.map { s -> s.wideQualification }.toIntArray())
            val wideFinals = Utils.getHighestScore(stats.map { s -> s.wideFinals }.toIntArray())
            val total = qualification + finals + lobby + wideQualification + wideFinals

            val combinedStats = PracticeStats(wallyUser.mainAccountUuid, "${wallyUser.member.effectiveName}#${wallyUser.member.user.discriminator}", qualification, finals, lobby, wideQualification, wideFinals, total)

            event.deferEdit().queue()

            val data2 = DatabaseUtils.getLocalData(uuid2, DatabaseUtils.DataType.PRACTICE)
            sendPracticeCompare(menuUser, event.message, combinedStats, PracticeStats(data2!!), true)
        } else {
            val uuid = event.selectedOptions[0].value.split(":").first()
            val wallyUser = getWallyUserFromUUID(uuid)
            val memberId = wallyUser!!.member.id
            val filePath = Paths.get("database/linked users/$memberId/accounts/$uuid/practice.json")
            var data = ""
            try {
                data = String(Files.readAllBytes(filePath))
            } catch (e: Exception) {
                Wally.logger.logStackTrace(e)
            }

            event.deferEdit().queue()

            val data2 = DatabaseUtils.getLocalData(uuid2, DatabaseUtils.DataType.PRACTICE)
            sendPracticeCompare(menuUser, event.message, PracticeStats(data), PracticeStats(data2!!), true)
        }
    }
}
