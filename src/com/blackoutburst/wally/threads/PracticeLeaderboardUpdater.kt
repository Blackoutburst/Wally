package com.blackoutburst.wally.threads

import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.request.PracticeServer
import com.blackoutburst.wally.utils.DatabaseUtils
import java.lang.Thread.sleep

object PracticeLeaderboardUpdater {

    fun run() {
        val thread = Thread {
            while (true) {
                for (i in 0 until LeaderboardManager.qualificationLeaderboardPractice.players.size) {
                    val player = LeaderboardManager.qualificationLeaderboardPractice.players[i]
                    DatabaseUtils.updateLeaderboardStats(PracticeServer.getPlayerStats(player.uuid, true) ?: continue,
                        DatabaseUtils.DataType.PRACTICE, player.uuid)
                    sleep(1000)
                }
            }
        }
        thread.start()
        thread.name = "Practice Leaderboard Updater"
    }
}