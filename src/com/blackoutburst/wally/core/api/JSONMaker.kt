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
        for (user in LeaderboardManager.winsLeaderboardHypixel.players) {
            user as HypixelStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.wins}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixelwall\": [")
        for (user in LeaderboardManager.wallLeaderboardHypixel.players) {
            user as HypixelStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.walls}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixelqualification\": [")
        for (user in LeaderboardManager.qualificationLeaderboardHypixel.players) {
            user as HypixelStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.qualification}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixelfinals\": [")
        for (user in LeaderboardManager.finalsLeaderboardHypixel.players) {
            user as HypixelStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.finals}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixeltotal\": [")
        for (user in LeaderboardManager.totalLeaderboardHypixel.players) {
            user as HypixelStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.total}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixeltotalapi\": [")
        for (user in LeaderboardManager.totalApiLeaderboardHypixel.players) {
            user as HypixelStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.totalAPI}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixelchallenges\": [")
        for (user in LeaderboardManager.challengesLeaderboardHypixel.players) {
            user as HypixelStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.challenges}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"hypixeloverall\": [")
        for (user in LeaderboardManager.overallLeaderboardHypixel.players) {
            user as HypixelStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.overall}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicequalification\": [")
        for (user in LeaderboardManager.qualificationLeaderboardPractice.players) {
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.qualification}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicefinals\": [")
        for (user in LeaderboardManager.finalsLeaderboardPractice.players) {
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.finals}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicelobby\": [")
        for (user in LeaderboardManager.lobbyLeaderboardPractice.players) {
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.lobby}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicewidequalification\": [")
        for (user in LeaderboardManager.wideQualificationLeaderboardPractice.players) {
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.wideQualification}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicewidefinals\": [")
        for (user in LeaderboardManager.wideFinalsLeaderboardPractice.players) {
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.wideFinals}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practicetotal\": [")
        for (user in LeaderboardManager.totalLeaderboardPractice.players) {
            user as PracticeStats
            builder.append("{\"name\": \"${user.name}\",\"uuid\": \"${user.uuid}\", \"point\": ${user.total}, \"club\": ${if (user.qualification > user.finals) user.qualification else user.finals}},")
        }
        builder = StringBuilder(builder.substring(0, builder.length-1))
        builder.append("],")

        builder.append("\"practiceoverall\": [")
        for (user in LeaderboardManager.overallLeaderboardPractice.players) {
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