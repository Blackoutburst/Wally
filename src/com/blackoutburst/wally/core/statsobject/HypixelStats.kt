package com.blackoutburst.wally.core.statsobject

import com.blackoutburst.wally.utils.APIUtils.getValuesFromAPI
import com.blackoutburst.wally.utils.LeaderboardUtils.getPlayerPosition
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.leaderboard.Leaderboard

class HypixelStats(data: String, vararg noLeaderboard: Boolean) : StatsObject {
    override val uuid: String
    override val name: String
    val wins: Int
    val walls: Int
    override val qualification: Int
    override val finals: Int
    override val total: Int
    val totalAPI: Int
    val challenges: Int
    override var overall = 0f
    var winsPosition = 0
    var wallsPosition = 0
    var qualificationPosition = 0
    var finalsPosition = 0
    var totalPosition = 0
    var totalAPIPosition = 0
    var challengesPosition = 0
    var overallPosition = 0

    init {
        uuid = getValuesFromAPI(data, "player.uuid", "unknown")
        name = getValuesFromAPI(data, "player.displayname", "Unknown")
        wins = getValuesFromAPI(data, "player.stats.Arcade.wins_hole_in_the_wall", "0").toInt()
        walls = getValuesFromAPI(data, "player.stats.Arcade.rounds_hole_in_the_wall", "0").toInt()
        qualification = getValuesFromAPI(data, "player.stats.Arcade.hitw_record_q", "0").toInt()
        finals = getValuesFromAPI(data, "player.stats.Arcade.hitw_record_f", "0").toInt()
        total = qualification + finals
        totalAPI = getValuesFromAPI(data, "player.achievements.arcade_hitw_practice_makes_perfect", "0").toInt()
        challenges = getValuesFromAPI(data, "player.challenges.all_time.ARCADE__hole_in_the_wall_challenge", "0").toInt()

        if (noLeaderboard.isNotEmpty() && noLeaderboard[0]) {
            winsPosition = 0
            wallsPosition = 0
            qualificationPosition = 0
            finalsPosition = 0
            totalPosition = 0
            totalAPIPosition = 0
            challengesPosition = 0
            overall = 0f
            overallPosition = 0
        } else {
            winsPosition = getPlayerPosition(uuid, LeaderboardManager.winsLeaderboardHypixel)
            wallsPosition = getPlayerPosition(uuid, LeaderboardManager.wallLeaderboardHypixel)
            qualificationPosition =
                getPlayerPosition(uuid, LeaderboardManager.qualificationLeaderboardHypixel)
            finalsPosition = getPlayerPosition(uuid, LeaderboardManager.finalsLeaderboardHypixel)
            totalPosition = getPlayerPosition(uuid, LeaderboardManager.totalLeaderboardHypixel)
            totalAPIPosition = getPlayerPosition(uuid, LeaderboardManager.totalApiLeaderboardHypixel)
            challengesPosition = getPlayerPosition(uuid, LeaderboardManager.challengesLeaderboardHypixel)
            overall =
                (winsPosition + wallsPosition + qualificationPosition + finalsPosition + totalPosition + challengesPosition) / 6.0f
            overallPosition = getPlayerPosition(uuid, LeaderboardManager.overallLeaderboardHypixel)
        }
    }

    override fun getDataFromType(type: Leaderboard.Type): Int {
        return when (type) {
            Leaderboard.Type.WINS -> wins
            Leaderboard.Type.WALLS -> walls
            Leaderboard.Type.QUALIFICATION -> qualification
            Leaderboard.Type.FINALS -> finals
            Leaderboard.Type.TOTAL -> total
            Leaderboard.Type.TOTAL_API -> totalAPI
            Leaderboard.Type.CHALLENGES -> challenges
            else -> 0
        }
    }

    override val scores: IntArray
        get() = intArrayOf(qualification, finals)
}