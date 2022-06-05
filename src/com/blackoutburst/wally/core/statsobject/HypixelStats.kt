package com.blackoutburst.wally.core.statsobject

import com.blackoutburst.wally.utils.APIUtils.getValuesFromAPI
import com.blackoutburst.wally.utils.LeaderboardUtils.getPlayerPosition
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.leaderboard.Leaderboard

class HypixelStats() : StatsObject {

    override var uuid = ""
    override var name = ""
    var wins = 0
    var walls = 0
    override var qualification = 0
    override var finals = 0
    override var total = 0
    var totalAPI = 0
    var challenges = 0
    override var overall = 0f
    var winsPosition = 0
    var wallsPosition = 0
    var qualificationPosition = 0
    var finalsPosition = 0
    var totalPosition = 0
    var totalAPIPosition = 0
    var challengesPosition = 0
    var overallPosition = 0
    var combined = false

    constructor(uuid: String, name: String, wins: Int, walls: Int, qualification: Int, finals: Int, total: Int, totalAPI: Int, challenges: Int) : this() {
        this.uuid = uuid
        this.name = name
        this.wins = wins
        this.walls = walls
        this.qualification = qualification
        this.finals = finals
        this.total = total
        this.totalAPI = totalAPI
        this.challenges = challenges
        this.combined = true

        winsPosition = getPlayerPosition(uuid, LeaderboardManager.winsLeaderboardHypixel)
        wallsPosition = getPlayerPosition(uuid, LeaderboardManager.wallLeaderboardHypixel)
        qualificationPosition = getPlayerPosition(uuid, LeaderboardManager.qualificationLeaderboardHypixel)
        finalsPosition = getPlayerPosition(uuid, LeaderboardManager.finalsLeaderboardHypixel)
        totalPosition = getPlayerPosition(uuid, LeaderboardManager.totalLeaderboardHypixel)
        totalAPIPosition = getPlayerPosition(uuid, LeaderboardManager.totalApiLeaderboardHypixel)
        challengesPosition = getPlayerPosition(uuid, LeaderboardManager.challengesLeaderboardHypixel)
        overall = (winsPosition + wallsPosition + qualificationPosition + finalsPosition + totalPosition + challengesPosition) / 6.0f
        overallPosition = getPlayerPosition(uuid, LeaderboardManager.overallLeaderboardHypixel)
    }

    constructor(data: String, vararg noLeaderboard: Boolean) : this() {
        uuid = getValuesFromAPI(data, "player.uuid", "unknown")
        name = getValuesFromAPI(data, "player.displayname", "Unknown")
        wins = getValuesFromAPI(data, "player.stats.Arcade.wins_hole_in_the_wall", "0").toInt()
        walls = getValuesFromAPI(data, "player.stats.Arcade.rounds_hole_in_the_wall", "0").toInt()
        qualification = getValuesFromAPI(data, "player.stats.Arcade.hitw_record_q", "0").toInt()
        finals = getValuesFromAPI(data, "player.stats.Arcade.hitw_record_f", "0").toInt()
        total = qualification + finals
        totalAPI = getValuesFromAPI(data, "player.achievements.arcade_hitw_practice_makes_perfect", "0").toInt()
        challenges = getValuesFromAPI(data, "player.challenges.all_time.ARCADE__hole_in_the_wall_challenge", "0").toInt()

        if (noLeaderboard.isEmpty() || noLeaderboard.isNotEmpty() && !noLeaderboard[0]) {
            winsPosition = getPlayerPosition(uuid, LeaderboardManager.winsLeaderboardHypixel)
            wallsPosition = getPlayerPosition(uuid, LeaderboardManager.wallLeaderboardHypixel)
            qualificationPosition = getPlayerPosition(uuid, LeaderboardManager.qualificationLeaderboardHypixel)
            finalsPosition = getPlayerPosition(uuid, LeaderboardManager.finalsLeaderboardHypixel)
            totalPosition = getPlayerPosition(uuid, LeaderboardManager.totalLeaderboardHypixel)
            totalAPIPosition = getPlayerPosition(uuid, LeaderboardManager.totalApiLeaderboardHypixel)
            challengesPosition = getPlayerPosition(uuid, LeaderboardManager.challengesLeaderboardHypixel)
            overall = (winsPosition + wallsPosition + qualificationPosition + finalsPosition + totalPosition + challengesPosition) / 6.0f
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