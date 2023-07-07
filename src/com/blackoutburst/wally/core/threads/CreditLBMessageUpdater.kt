package com.blackoutburst.wally.core.threads

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.statsobject.PracticeStats
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.lang.Thread.sleep
import java.time.ZoneId
import java.time.ZonedDateTime

object CreditLBMessageUpdater {

    fun run() {
        val thread = Thread {
            while (true) {
                try {
                    val playersByScores = LeaderboardManager.creditLeaderboardPractice.players.groupBy {
                        (it.finals + it.qualification) / 50 * 50
                    }

                    val tmp = EmbedBuilder()
                        .setAuthor("Credits Leaderboard", "https://www.hitw.community/", "https://raw.githubusercontent.com/Blackoutburst/Wally/master/res/icon_credits.png")
                        .setColor(Color(242, 194, 0))
                        .setFooter("Updated on")
                        .setTimestamp(ZonedDateTime.now(ZoneId.of("America/New_York")))

                    playersByScores.entries
                    .filter { (club, _) -> club >= 200 }
                    .sortedByDescending { (club, _) -> club }
                    .forEach { (club, players) ->
                        val name = "**${club}+ (QF)**"
                        var value = "```yml\n"
                        players.take(3).forEach {
                            value += "${it.name}: ${Wally.numberFormat.format((it as PracticeStats).credits)}\n"
                        }
                        value += "```\n"

                        tmp.addField(name, value, true)
                    }

                    Wally.hitwDiscord!!.getTextChannelById(ConfigManager.loadValue("channels", "credit leaderboard channel"))!!
                        .editMessageEmbedsById(ConfigManager.loadValue("messages", "credit leaderboard"), tmp.build()).queue()
                    sleep(60000)
                } catch (e: Exception) {
                    Wally.logger.logStackTrace(e)
                }
            }
        }
        thread.start()
        thread.name = "Credit leaderboard message updater"
    }
}