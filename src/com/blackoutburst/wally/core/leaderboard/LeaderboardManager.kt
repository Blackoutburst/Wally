package com.blackoutburst.wally.core.leaderboard

import com.blackoutburst.wally.core.leaderboard.comparators.HypixelWinsComparator
import com.blackoutburst.wally.core.leaderboard.comparators.HypixelWallsComparator
import com.blackoutburst.wally.core.leaderboard.comparators.QualificationComparator
import com.blackoutburst.wally.core.leaderboard.comparators.FinalsComparator
import com.blackoutburst.wally.core.leaderboard.comparators.TotalComparator
import com.blackoutburst.wally.core.leaderboard.comparators.HypixelTotalAPIComparator
import com.blackoutburst.wally.core.leaderboard.comparators.HypixelChallengesComparator
import com.blackoutburst.wally.core.leaderboard.comparators.OverallComparator
import com.blackoutburst.wally.core.leaderboard.comparators.PracticeLobbyComparator
import com.blackoutburst.wally.core.leaderboard.comparators.PracticeWideQualificationComparator
import com.blackoutburst.wally.core.leaderboard.comparators.PracticeWideFinalsComparator
import com.blackoutburst.wally.core.statsobject.StatsObject
import java.nio.file.Paths
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.utils.DatabaseUtils
import java.io.File
import java.nio.file.Files
import java.time.Duration
import java.time.Instant

object LeaderboardManager {

    val winsLeaderboardHypixel = Leaderboard("Hypixel Wins", HypixelWinsComparator(), Leaderboard.Type.WINS)
    val wallLeaderboardHypixel = Leaderboard("Hypixel Walls", HypixelWallsComparator(), Leaderboard.Type.WALLS)
    val qualificationLeaderboardHypixel = Leaderboard("Hypixel Qualification", QualificationComparator(), Leaderboard.Type.QUALIFICATION)
    val finalsLeaderboardHypixel = Leaderboard("Hypixel Finals", FinalsComparator(), Leaderboard.Type.FINALS)
    val totalLeaderboardHypixel = Leaderboard("Hypixel Total Score", TotalComparator(), Leaderboard.Type.TOTAL)
    val totalApiLeaderboardHypixel = Leaderboard("Hypixel Total Score (API)", HypixelTotalAPIComparator(), Leaderboard.Type.TOTAL_API)
    val challengesLeaderboardHypixel = Leaderboard("Hypixel Daily Challenges", HypixelChallengesComparator(), Leaderboard.Type.CHALLENGES)
    val overallLeaderboardHypixel = Leaderboard("Hypixel Overall Rank", OverallComparator(), Leaderboard.Type.OVERALL)
    val qualificationLeaderboardPractice = Leaderboard("Practice Qualification", QualificationComparator(), Leaderboard.Type.QUALIFICATION)
    val finalsLeaderboardPractice = Leaderboard("Practice Finals", FinalsComparator(), Leaderboard.Type.FINALS)
    val lobbyLeaderboardPractice = Leaderboard("Practice Lobby", PracticeLobbyComparator(), Leaderboard.Type.LOBBY)
    val wideQualificationLeaderboardPractice = Leaderboard("Practice Wide Qualification", PracticeWideQualificationComparator(), Leaderboard.Type.WIDE_QUALIFICATION)
    val wideFinalsLeaderboardPractice = Leaderboard("Practice Wide Finals", PracticeWideFinalsComparator(), Leaderboard.Type.WIDE_FINALS)
    val totalLeaderboardPractice = Leaderboard("Practice Total Score", TotalComparator(), Leaderboard.Type.TOTAL)
    val overallLeaderboardPractice = Leaderboard("Practice Overall Rank", OverallComparator(), Leaderboard.Type.OVERALL)

    private fun calculateOverallLeaderboards() {
        populate(overallLeaderboardHypixel.players, DatabaseUtils.DataType.HYPIXEL, false)
        sort(overallLeaderboardHypixel)
        populate(overallLeaderboardPractice.players, DatabaseUtils.DataType.PRACTICE, false)
        sort(overallLeaderboardPractice)
    }

    private fun populate(leaderboard: MutableList<StatsObject>, type: DatabaseUtils.DataType, skipPosition: Boolean) {
        val rootFolder = File("database/leaderboards/" + type.dataName)
        val entries = rootFolder.list() ?: return

        for (s in entries) {
            val userFolder = File(rootFolder.path, s)
            val filePath = Paths.get(userFolder.path + "/data.json")

            try {
                val data = String(Files.readAllBytes(filePath))
                leaderboard.add(
                    if (type == DatabaseUtils.DataType.HYPIXEL) HypixelStats(data, skipPosition)
                    else PracticeStats(data, skipPosition)
                )
            } catch (e: Exception) {
                Wally.logger.logStackTrace(e)
            }
        }
    }

    private fun sort(leaderboard: Leaderboard) {
        leaderboard.players.sortWith(leaderboard.comparator)
    }

    fun addPracticePlayer(data: String) {
        val playerData = PracticeStats(data, true)
        if (qualificationLeaderboardPractice.players.find { player -> player.uuid == playerData.uuid } != null) {
            return
        }

        qualificationLeaderboardPractice.players.add(playerData)
        finalsLeaderboardPractice.players.add(playerData)
        lobbyLeaderboardPractice.players.add(playerData)
        wideQualificationLeaderboardPractice.players.add(playerData)
        wideFinalsLeaderboardPractice.players.add(playerData)
        totalLeaderboardPractice.players.add(playerData)

        sort(qualificationLeaderboardPractice)
        sort(finalsLeaderboardPractice)
        sort(lobbyLeaderboardPractice)
        sort(wideQualificationLeaderboardPractice)
        sort(wideFinalsLeaderboardPractice)
        sort(totalLeaderboardPractice)

        val playerOverallData = PracticeStats(data, false)
        overallLeaderboardPractice.players.add(playerOverallData)
        sort(overallLeaderboardPractice)
    }

    fun addHypixelPlayer(data :String) {
        val playerData = HypixelStats(data, true)
        if (winsLeaderboardHypixel.players.find { player -> player.uuid == playerData.uuid } != null) {
            return
        }

        winsLeaderboardHypixel.players.add(playerData)
        wallLeaderboardHypixel.players.add(playerData)
        qualificationLeaderboardHypixel.players.add(playerData)
        finalsLeaderboardHypixel.players.add(playerData)
        totalLeaderboardHypixel.players.add(playerData)
        totalApiLeaderboardHypixel.players.add(playerData)
        challengesLeaderboardHypixel.players.add(playerData)

        sort(winsLeaderboardHypixel)
        sort(wallLeaderboardHypixel)
        sort(qualificationLeaderboardHypixel)
        sort(finalsLeaderboardHypixel)
        sort(totalLeaderboardHypixel)
        sort(totalApiLeaderboardHypixel)
        sort(challengesLeaderboardHypixel)

        val playerOverallData = HypixelStats(data, false)
        overallLeaderboardHypixel.players.add(playerOverallData)
        sort(overallLeaderboardHypixel)

    }

    fun removePlayer(uuid : String) {
        if (winsLeaderboardHypixel.players.find { player -> player.uuid == uuid } != null) {
            winsLeaderboardHypixel.players.remove(winsLeaderboardHypixel.players.first { player -> player.uuid == uuid })
            wallLeaderboardHypixel.players.remove(wallLeaderboardHypixel.players.first { player -> player.uuid == uuid })
            qualificationLeaderboardHypixel.players.remove(qualificationLeaderboardHypixel.players.first { player -> player.uuid == uuid })
            finalsLeaderboardHypixel.players.remove(finalsLeaderboardHypixel.players.first { player -> player.uuid == uuid })
            totalLeaderboardHypixel.players.remove(totalLeaderboardHypixel.players.first { player -> player.uuid == uuid })
            totalApiLeaderboardHypixel.players.remove(totalApiLeaderboardHypixel.players.first { player -> player.uuid == uuid })
            challengesLeaderboardHypixel.players.remove(challengesLeaderboardHypixel.players.first { player -> player.uuid == uuid })
            overallLeaderboardHypixel.players.remove(overallLeaderboardHypixel.players.first { player -> player.uuid == uuid })
        }

        if (qualificationLeaderboardPractice.players.find { player -> player.uuid == uuid } != null) {
            qualificationLeaderboardPractice.players.remove(qualificationLeaderboardPractice.players.first { player -> player.uuid == uuid })
            finalsLeaderboardPractice.players.remove(finalsLeaderboardPractice.players.first { player -> player.uuid == uuid })
            lobbyLeaderboardPractice.players.remove(lobbyLeaderboardPractice.players.first { player -> player.uuid == uuid })
            wideQualificationLeaderboardPractice.players.remove(wideQualificationLeaderboardPractice.players.first { player -> player.uuid == uuid })
            wideFinalsLeaderboardPractice.players.remove(wideFinalsLeaderboardPractice.players.first { player -> player.uuid == uuid })
            totalLeaderboardPractice.players.remove(totalLeaderboardPractice.players.first { player -> player.uuid == uuid })
            overallLeaderboardPractice.players.remove(overallLeaderboardPractice.players.first { player -> player.uuid == uuid })
        }
    }

    init {
        Wally.logger.log(LogLevel.INFO, "Initializing leaderboard")
        val begin = Instant.now()

        populate(winsLeaderboardHypixel.players, DatabaseUtils.DataType.HYPIXEL, true)
        sort(winsLeaderboardHypixel)
        wallLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players)
        qualificationLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players)
        finalsLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players)
        totalLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players)
        totalApiLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players)
        challengesLeaderboardHypixel.players.addAll(winsLeaderboardHypixel.players)
        populate(qualificationLeaderboardPractice.players, DatabaseUtils.DataType.PRACTICE, true)
        sort(qualificationLeaderboardPractice)
        finalsLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players)
        lobbyLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players)
        wideQualificationLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players)
        wideFinalsLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players)
        totalLeaderboardPractice.players.addAll(qualificationLeaderboardPractice.players)
        sort(wallLeaderboardHypixel)
        sort(qualificationLeaderboardHypixel)
        sort(finalsLeaderboardHypixel)
        sort(totalLeaderboardHypixel)
        sort(totalApiLeaderboardHypixel)
        sort(challengesLeaderboardHypixel)
        sort(finalsLeaderboardPractice)
        sort(lobbyLeaderboardPractice)
        sort(wideQualificationLeaderboardPractice)
        sort(wideFinalsLeaderboardPractice)
        sort(totalLeaderboardPractice)

        calculateOverallLeaderboards()

        Wally.logger.log(LogLevel.INFO, "Leaderboard initialized in " +
            Wally.decimalFormat.format(Duration.between(begin, Instant.now()).toMillis() / 1000.0f).toString() + "s")

        Wally.isReady = true
    }
}