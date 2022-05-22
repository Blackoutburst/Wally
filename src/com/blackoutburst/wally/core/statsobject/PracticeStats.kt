package com.blackoutburst.wally.core.statsobject

import com.blackoutburst.wally.utils.APIUtils.getValuesFromAPI
import com.blackoutburst.wally.utils.LeaderboardUtils.getPlayerPosition
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.leaderboard.Leaderboard

class PracticeStats(data: String?, vararg noLeaderboard: Boolean) : StatsObject {

    override val uuid: String
    override val name: String
    override val qualification: Int
    override val finals: Int
    val lobby: Int
    val wideQualification: Int
    val wideFinals: Int
    override val total: Int
    override var overall = 0f
    var qualificationPosition = 0
    var finalsPosition = 0
    var lobbyPosition = 0
    var wideQualificationPosition = 0
    var wideFinalsPosition = 0
    var totalPosition = 0
    var overallPosition = 0

    init {
        uuid = getValuesFromAPI(data!!, "uuid", "unknown")
        name = getValuesFromAPI(data, "name", "Unknown")
        qualification = getValuesFromAPI(data, "scores.qualification", "0").toInt()
        finals = getValuesFromAPI(data, "scores.finals", "0").toInt()
        lobby = getValuesFromAPI(data, "scores.lobby", "0").toInt()
        wideQualification = getValuesFromAPI(data, "scores.wide_qualification", "0").toInt()
        wideFinals = getValuesFromAPI(data, "scores.wide_finals", "0").toInt()
        total = qualification + finals + lobby + wideQualification + wideFinals
        if (noLeaderboard.isNotEmpty() && noLeaderboard[0]) {
            qualificationPosition = 0
            finalsPosition = 0
            lobbyPosition = 0
            wideQualificationPosition = 0
            wideFinalsPosition = 0
            totalPosition = 0
            overall = 0f
            overallPosition = 0
        } else {
            qualificationPosition = getPlayerPosition(uuid, LeaderboardManager.qualificationLeaderboardPractice)
            finalsPosition = getPlayerPosition(uuid, LeaderboardManager.finalsLeaderboardPractice)
            lobbyPosition = getPlayerPosition(uuid, LeaderboardManager.lobbyLeaderboardPractice)
            wideQualificationPosition = getPlayerPosition(uuid, LeaderboardManager.wideQualificationLeaderboardPractice)
            wideFinalsPosition = getPlayerPosition(uuid, LeaderboardManager.wideFinalsLeaderboardPractice)
            totalPosition = getPlayerPosition(uuid, LeaderboardManager.totalLeaderboardPractice)
            overall = (qualificationPosition + finalsPosition + wideQualificationPosition + wideFinalsPosition + totalPosition) / 5.0f
            overallPosition = getPlayerPosition(uuid, LeaderboardManager.overallLeaderboardPractice)
        }
    }

    override fun getDataFromType(type: Leaderboard.Type): Int {
        return when (type) {
            Leaderboard.Type.QUALIFICATION -> qualification
            Leaderboard.Type.FINALS -> finals
            Leaderboard.Type.LOBBY -> lobby
            Leaderboard.Type.WIDE_QUALIFICATION -> wideQualification
            Leaderboard.Type.WIDE_FINALS -> wideFinals
            Leaderboard.Type.TOTAL -> total
            else -> 0
        }
    }

    override val scores: IntArray
        get() = intArrayOf(qualification, finals, lobby, wideQualification, wideFinals)
}