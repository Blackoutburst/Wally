package com.blackoutburst.wally.utils

import com.blackoutburst.wally.utils.Utils.getColorFromScore
import com.blackoutburst.wally.utils.Utils.getHighestScore
import com.blackoutburst.wally.core.leaderboard.Leaderboard
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.EmbedBuilder
import java.time.LocalDate
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.statsobject.HypixelStats
import java.awt.Color
import java.io.BufferedReader
import java.io.FileReader
import java.lang.Exception
import java.lang.StringBuilder

object EmbedUtils {
    /**
     * Create an embed with leaderboard information inside
     * @param leaderboard the leaderboard
     * @param page the leaderboard page
     * @return an embed containing every hole in the wall stats
     */
    fun generateLeaderboardEmbed(leaderboard: Leaderboard, page: Int): MessageEmbed {
        var leaderboardPage = (page - 1) * 10

        if (leaderboard.players.size <= leaderboardPage) {
            leaderboardPage = leaderboard.players.size - 10
        }

        val topPlayer = leaderboard.players[leaderboardPage]

        return EmbedBuilder()
            .setAuthor(leaderboard.name,"https://hypixel.net/arcade-games/leaderboard/hole_in_the_wall","https://www.blackoutburst.com/img/wally.png")
            .setColor(getColorFromScore(getHighestScore(topPlayer.scores)))
            .setDescription(MessageUtils.generateLeaderboardMessage(leaderboard, page, false))
            .setFooter(LocalDate.now().toString() + " | " + leaderboard.type)
            .build()
    }

    /**
     * Create an embed with Practice stats information inside
     * @param data a valid Practice json data
     * @return an embed containing every hole in the wall stats
     */
    fun generatePracticeStatsEmbed(data: String): MessageEmbed {
        val time = System.currentTimeMillis()
        val stats = PracticeStats(data)
        val uuid = stats.uuid
        return EmbedBuilder()
            .setAuthor(stats.name,"https://hypixel.net/player/$uuid","https://visage.surgeplay.com/face/512/$uuid?&time=$time")
            .setThumbnail("https://visage.surgeplay.com/face/512/$uuid?&time=$time")
            .setTitle("Practice stats", "https://hypixel.net/player/$uuid")
            .setColor(getColorFromScore(getHighestScore(stats.scores)))
            .addField("Qualification", Utils.formatLine(stats.qualification, stats.qualificationPosition),true)
            .addField("Finals", Utils.formatLine(stats.finals, stats.finalsPosition),true)
            .addBlankField(true)
            .addField("Lobby", Utils.formatLine(stats.lobby, stats.lobbyPosition),true)
            .addBlankField(true)
            .addBlankField(true)
            .addField("Wide Qualification", Utils.formatLine(stats.wideQualification, stats.wideQualificationPosition),true)
            .addField("Wide Finals", Utils.formatLine(stats.wideFinals, stats.wideFinalsPosition),true)
            .addBlankField(true)
            .addField("Total Score", Utils.formatLine(stats.total, stats.totalPosition),true)
            .addField("Overall Rank", Utils.formatLine(stats.overall, stats.overallPosition),true)
            .setFooter(LocalDate.now().toString() + " | " + stats.uuid)
            .build()
    }

    /**
     * Create an embed with Hypixel stats information inside
     * @param data a valid Hypixel json data
     * @return an embed containing every hole in the wall stats
     */
    fun generateHypixelStatsEmbed(data: String): MessageEmbed {
        val time = System.currentTimeMillis()
        val stats = HypixelStats(data)
        val uuid = stats.uuid
        val date = LocalDate.now().toString()

        return EmbedBuilder()
            .setAuthor(stats.name,"https://hypixel.net/player/$uuid","https://visage.surgeplay.com/face/512/$uuid?&time=$time")
            .setThumbnail("https://visage.surgeplay.com/face/512/" + stats.uuid + "?&time=" + time)
            .setTitle("Hypixel stats", "https://hypixel.net/player/" + stats.uuid)
            .setColor(getColorFromScore(getHighestScore(stats.scores)))
            .addField("Wins", Utils.formatLine(stats.wins, stats.winsPosition),true)
            .addField("Walls", Utils.formatLine(stats.walls, stats.wallsPosition),true)
            .addBlankField(true)
            .addField("Qualification", Utils.formatLine(stats.qualification, stats.qualificationPosition),true)
            .addField("Finals", Utils.formatLine(stats.finals, stats.finalsPosition),true)
            .addBlankField(true)
            .addField("QF", Utils.formatLine(stats.total, stats.totalPosition),true)
            .addField("QF (according to the API)", Utils.formatLine(stats.totalAPI, stats.totalAPIPosition),true)
            .addBlankField(true)
            .addField("Challenges", Utils.formatLine(stats.challenges, stats.challengesPosition),true)
            .addField("Overall Rank", Utils.formatLine(stats.overall, stats.overallPosition),true)
            .setFooter("$date | $uuid")
            .build()
    }

    /**
     * Generate the skin preview in embed
     *
     * @param uuid the player uuid
     * @param playerName the player name
     * @return the skin embed
     */
    fun generateSkin(uuid: String, playerName: String): EmbedBuilder {
        val time = System.currentTimeMillis()
        return EmbedBuilder()
            .setAuthor(playerName,"https://fr.namemc.com/profile/$uuid","https://visage.surgeplay.com/face/512/$uuid?&time=$time")
            .setTitle("NameMC", "https://fr.namemc.com/profile/$uuid")
            .setColor(Color(71, 165, 255))
            .setImage("https://visage.surgeplay.com/full/512/$uuid?&time=$time")
    }

    /**
     * Generate the changelog embed message
     * @return the changelog embed
     */
    fun generateChangelog(): EmbedBuilder? {
        return try {
            val wallyURL = "https://github.com/Blackoutburst/Wally"
            val wallyPfp = "https://www.blackoutburst.com/img/wally.png"
            val reader = BufferedReader(FileReader("config/changelog.txt"))
            val changelog = StringBuilder()
            val title = reader.readLine()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                changelog.append(line).append("\n")
            }
            reader.close()

            EmbedBuilder()
                .setAuthor(LocalDate.now().toString(), wallyURL, wallyPfp)
                .setTitle(title, wallyURL)
                .addField("Changes", changelog.toString(), false)
                .addField("", "[GitHub](https://github.com/Blackoutburst/Wally)", true)
                .addField("", "[Patreon](https://www.patreon.com/Blackoutburst)", true)
                .setColor(Color(71, 165, 255))
                .setFooter("Made with love by Blackoutburst", "https://www.blackoutburst.com/img/pfp.jpg")
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e.stackTrace)
            null
        }
    }
}