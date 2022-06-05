package com.blackoutburst.wally.core.statsobject

import com.blackoutburst.wally.utils.APIUtils.getValuesFromAPI
import com.blackoutburst.wally.utils.LeaderboardUtils.getPlayerPosition
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.leaderboard.Leaderboard

class PracticeStats() : StatsObject {

    override var uuid = ""
    override var name = ""
    override var qualification = 0
    override var finals = 0
    var lobby = 0
    var wideQualification = 0
    var wideFinals = 0
    override var total = 0
    override var overall = 0f
    var qualificationPosition = 0
    var finalsPosition = 0
    var lobbyPosition = 0
    var wideQualificationPosition = 0
    var wideFinalsPosition = 0
    var totalPosition = 0
    var overallPosition = 0
    var combined = false

    constructor(uuid: String, name: String, qualification: Int, finals: Int, lobby :Int, wideQualification: Int, wideFinals: Int, total: Int) : this() {
        this.uuid = uuid
        this.name = name
        this.qualification = qualification
        this.finals = finals
        this.lobby = lobby
        this.wideQualification = wideQualification
        this.wideFinals = wideFinals
        this.total = total
        this.combined = true

        qualificationPosition = getPlayerPosition(uuid, LeaderboardManager.qualificationLeaderboardPractice)
        finalsPosition = getPlayerPosition(uuid, LeaderboardManager.finalsLeaderboardPractice)
        lobbyPosition = getPlayerPosition(uuid, LeaderboardManager.lobbyLeaderboardPractice)
        wideQualificationPosition = getPlayerPosition(uuid, LeaderboardManager.wideQualificationLeaderboardPractice)
        wideFinalsPosition = getPlayerPosition(uuid, LeaderboardManager.wideFinalsLeaderboardPractice)
        totalPosition = getPlayerPosition(uuid, LeaderboardManager.totalLeaderboardPractice)
        overall = (qualificationPosition + finalsPosition + wideQualificationPosition + wideFinalsPosition + totalPosition) / 5.0f
        overallPosition = getPlayerPosition(uuid, LeaderboardManager.overallLeaderboardPractice)
    }

    constructor(data: String, vararg noLeaderboard: Boolean) : this() {
        uuid = getValuesFromAPI(data, "uuid", "unknown")
        name = getValuesFromAPI(data, "name", "Unknown")
        qualification = getValuesFromAPI(data, "scores.qualification", "0").toInt()
        finals = getValuesFromAPI(data, "scores.finals", "0").toInt()
        lobby = getValuesFromAPI(data, "scores.lobby", "0").toInt()
        wideQualification = getValuesFromAPI(data, "scores.wide_qualification", "0").toInt()
        wideFinals = getValuesFromAPI(data, "scores.wide_finals", "0").toInt()
        total = qualification + finals + lobby + wideQualification + wideFinals

        if (noLeaderboard.isEmpty() || noLeaderboard.isNotEmpty() && !noLeaderboard[0]) {
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