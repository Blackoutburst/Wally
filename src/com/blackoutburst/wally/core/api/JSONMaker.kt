package com.blackoutburst.wally.core.api

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.core.statsobject.PracticeStats
import java.nio.file.Files
import java.nio.file.Paths

object JSONMaker {

    fun createLeaderboard(): String {
        var builder = StringBuilder()
        builder.append("{")
        builder.append("\"hypixelwins\": [")
        var length = LeaderboardManager.winsLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.winsLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.wins}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixelwall\": [")
        length = LeaderboardManager.wallLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.wallLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.walls}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixelqualification\": [")
        length = LeaderboardManager.qualificationLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.qualificationLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.qualification}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixelfinals\": [")
        length = LeaderboardManager.finalsLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.finalsLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.finals}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixeltotal\": [")
        length = LeaderboardManager.totalLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.totalLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.total}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixeltotalapi\": [")
        length = LeaderboardManager.totalApiLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.totalApiLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.totalAPI}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixelchallenges\": [")
        length = LeaderboardManager.challengesLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.challengesLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.challenges}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixeloverall\": [")
        length = LeaderboardManager.overallLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.overallLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.overall}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicequalification\": [")
        length = LeaderboardManager.qualificationLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.qualificationLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.qualification}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicefinals\": [")
        length = LeaderboardManager.finalsLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.finalsLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.finals}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicelobby\": [")
        length = LeaderboardManager.lobbyLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.lobbyLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.lobby}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicewidequalification\": [")
        length = LeaderboardManager.wideQualificationLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.wideQualificationLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.wideQualification}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicewidefinals\": [")
        length = LeaderboardManager.wideFinalsLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.wideFinalsLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.wideFinals}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicetotal\": [")
        length = LeaderboardManager.totalLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.totalLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.total}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"credits\": [")
        length = LeaderboardManager.creditLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.creditLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.total}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"creditsearned\": [")
        length = LeaderboardManager.creditEarnedLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.creditEarnedLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.total}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practiceoverall\": [")
        length = LeaderboardManager.overallLeaderboardPractice.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.overallLeaderboardPractice.players[i]
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.overall}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("]")

        builder.append("}")

        return builder.toString()
    }

    fun createUserListJson(): String {
        var builder = StringBuilder()
        builder.append("{ \"users\": [")

        val length = LeaderboardManager.winsLeaderboardHypixel.players.size
        for (i in 0 until length) {
            val user = LeaderboardManager.winsLeaderboardHypixel.players[i]
            user as HypixelStats
            if (user.walls == 0) continue
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
                        "overallrank": ${pstats?.overall ?: 0},
                        "credits": ${pstats?.credits ?: 0},
                        "creditsearned": ${pstats?.creditsEarned ?: 0}
                    },
                    "positions": {
                        "qualification": ${pstats?.qualificationPosition ?: 0},
                        "finals": ${pstats?.finalsPosition ?: 0},
                        "lobby": ${pstats?.lobbyPosition ?: 0},
                        "widequalification": ${pstats?.wideQualificationPosition ?: 0},
                        "widefinals": ${pstats?.wideFinalsPosition ?: 0},
                        "total": ${pstats?.totalPosition ?: 0},
                        "overallrank": ${pstats?.overallPosition ?: 0},
                        "credits": ${pstats?.creditPosition ?: 0},
                        "creditsearned": ${pstats?.creditEarnedPosition ?: 0}
                    }
                }
            }
        """.trimIndent()
    }
}