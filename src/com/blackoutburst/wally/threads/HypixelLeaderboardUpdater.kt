package com.blackoutburst.wally.threads

import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.request.Hypixel
import com.blackoutburst.wally.utils.DatabaseUtils
import java.lang.Thread.sleep

object HypixelLeaderboardUpdater {

    fun run() {
        val thread = Thread {
            while (true) {
                for (i in 0..LeaderboardManager.winsLeaderboardHypixel.players.size) {
                    val player = LeaderboardManager.winsLeaderboardHypixel.players[i]
                    DatabaseUtils.updateLeaderboardStats(Hypixel.getPlayerStats(player.uuid, true) ?: continue,
                        DatabaseUtils.DataType.HYPIXEL, player.uuid)
                    sleep(1000)
                }
            }
        }
        thread.start()
        thread.name = "Hypixel Leaderboard Updater"
    }
}