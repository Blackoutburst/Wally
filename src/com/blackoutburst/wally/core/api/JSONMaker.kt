package com.blackoutburst.wally.core.api

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.leaderboard.Leaderboard
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.utils.DatabaseUtils
import java.nio.file.Files
import java.nio.file.Paths

object JSONMaker {

    fun createUserListJson(): String {
        var builder = StringBuilder()
        builder.append("{ \"users\": [")

        for (user in LeaderboardManager.winsLeaderboardHypixel.players) {
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }

        builder = StringBuilder(builder.substring(0, builder.length-1))

        builder.append("]}")

        return builder.toString()
    }

    fun createUserJson(uuid :String): String {
        val hpdata = try {
            val filePath = Paths.get("database/leaderboards/hypixel/$uuid/data.json")

            String(Files.readAllBytes(filePath))
        } catch (e2: Exception) {
            Wally.logger.logStackTrace(e2)
            return ("unknown player")
        }

        val pdata = try {
            val filePath = Paths.get("database/leaderboards/practice/$uuid/data.json")

            String(Files.readAllBytes(filePath))
        } catch (e2: Exception) {
            Wally.logger.logStackTrace(e2)
            null
        }

        val hpstats = HypixelStats(hpdata)
        val pstats = if (pdata == null) null else  PracticeStats(pdata)

        return """
            {
                "uuid": "${hpstats.uuid}",
                "name": "${hpstats.name}",
                "hypixel": {
                    "stats": {
                        "wins": ${hpstats.wins},
                        "walls": ${hpstats.walls},
                        "qualification": ${hpstats.qualification},
                        "finals": ${hpstats.finals},
                        "total": ${hpstats.total},
                        "totalapi": ${hpstats.totalAPI},
                        "challenges": ${hpstats.challenges},
                        "overallrank": ${hpstats.overall}
                    },
                    "positions": {
                        "wins": ${hpstats.winsPosition},
                        "walls": ${hpstats.wallsPosition},
                        "qualification": ${hpstats.qualificationPosition},
                        "finals": ${hpstats.finalsPosition},
                        "total": ${hpstats.totalPosition},
                        "totalapi": ${hpstats.totalAPIPosition},
                        "challenges": ${hpstats.challengesPosition},
                        "overallrank": ${hpstats.overallPosition}
                    }
                },
                "practice": {
                    "stats": {
                        "qualification": ${pstats?.qualification ?: 0},
                        "finals": ${pstats?.finals ?: 0},
                        "lobby": ${pstats?.lobby ?: 0},
                        "widequalification": ${pstats?.wideQualification ?: 0},
                        "widefinals": ${pstats?.wideFinals ?: 0},
                        "total": ${pstats?.total ?: 0},
                        "overallrank": ${pstats?.overall ?: 0}
                    },
                    "positions": {
                        "qualification": ${pstats?.qualificationPosition ?: 0},
                        "finals": ${pstats?.finalsPosition ?: 0},
                        "lobby": ${pstats?.lobbyPosition ?: 0},
                        "widequalification": ${pstats?.wideQualificationPosition ?: 0},
                        "widefinals": ${pstats?.wideFinalsPosition ?: 0},
                        "total": ${pstats?.totalPosition ?: 0},
                        "overallrank": ${pstats?.overallPosition ?: 0}
                    }
                }
            }
        """.trimIndent()
    }
}