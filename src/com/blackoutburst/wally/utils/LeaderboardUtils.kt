package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.leaderboard.Leaderboard
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.Wally

object LeaderboardUtils {

    fun getLeaderboardFromIndex(index: Int): Leaderboard {
        return when (index) {
            1 -> LeaderboardManager.wallLeaderboardHypixel
            2 -> LeaderboardManager.qualificationLeaderboardHypixel
            3 -> LeaderboardManager.finalsLeaderboardHypixel
            4 -> LeaderboardManager.totalLeaderboardHypixel
            5 -> LeaderboardManager.totalApiLeaderboardHypixel
            6 -> LeaderboardManager.challengesLeaderboardHypixel
            7 -> LeaderboardManager.overallLeaderboardHypixel
            8 -> LeaderboardManager.qualificationLeaderboardPractice
            9 -> LeaderboardManager.finalsLeaderboardPractice
            10 -> LeaderboardManager.lobbyLeaderboardPractice
            11 -> LeaderboardManager.wideQualificationLeaderboardPractice
            12 -> LeaderboardManager.wideFinalsLeaderboardPractice
            13 -> LeaderboardManager.totalLeaderboardPractice
            14 -> LeaderboardManager.overallLeaderboardPractice
            else -> LeaderboardManager.winsLeaderboardHypixel
        }
    }

    fun getPlayerPosition(uuid: String, leaderboard: Leaderboard): Int {
        var position = 0

        for (stats in leaderboard.players) {
            position++
            if (uuid == stats.uuid) return position
        }
        return 0
    }

    fun formatPosition(position: Int): String {
        return if (position == 0) "" else "(#" + Wally.numberFormat.format(position.toLong()) + ")"
    }
}