package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.leaderboard.Leaderboard
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.core.statsobject.StatsObject
import com.blackoutburst.wally.utils.LineUtils.comparisonLineLeft
import com.blackoutburst.wally.utils.LineUtils.comparisonLineMiddle
import com.blackoutburst.wally.utils.LineUtils.comparisonLineRight
import com.blackoutburst.wally.utils.Utils.getColorFromScore
import com.blackoutburst.wally.utils.Utils.getHighestScore
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.io.BufferedReader
import java.io.FileReader
import java.time.LocalDate


object EmbedUtils {

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

    fun generatePracticeStatsEmbed(stats: PracticeStats): MessageEmbed {
        val user = DatabaseUtils.getWallyUserFromUUID(stats.uuid)
        val time = System.currentTimeMillis()
        val uuid = stats.uuid
        val date = LocalDate.now().toString()
        val faceLink = if (stats.combined) user!!.member.effectiveAvatarUrl else "https://visage.surgeplay.com/face/512/$uuid?&time=$time"

        val builder = EmbedBuilder()
            .setAuthor(stats.name,"https://hypixel.net/player/$uuid",faceLink)
            .setThumbnail(faceLink)
            .setTitle("Practice stats", "https://hypixel.net/player/$uuid")
            .setColor(getColorFromScore(getHighestScore(stats.scores)))
            .addField("Qualification", "`"+Utils.formatLine(stats.qualification, stats.qualificationPosition)+"`",true)
            .addField("Finals", "`"+Utils.formatLine(stats.finals, stats.finalsPosition)+"`",true)
            .addBlankField(true)
            .addField("Lobby", "`"+Utils.formatLine(stats.lobby, stats.lobbyPosition)+"`",true)
            .addBlankField(true)
            .addBlankField(true)
            .addField("Wide Qualification", "`"+Utils.formatLine(stats.wideQualification, stats.wideQualificationPosition)+"`",true)
            .addField("Wide Finals", "`"+Utils.formatLine(stats.wideFinals, stats.wideFinalsPosition)+"`",true)
            .addBlankField(true)
            .addField("Total Score", "`"+Utils.formatLine(stats.total, stats.totalPosition)+"`",true)

        if (stats.overall.toInt() != 0)
            builder.addField("Overall Rank", "`"+Utils.formatLine(stats.overall, stats.overallPosition)+"`",true)
        builder.addBlankField(true)
        builder.setFooter("$date | $uuid")

        return builder.build()
    }

    fun generateHypixelStatsEmbed(stats: HypixelStats): MessageEmbed {
        val user = DatabaseUtils.getWallyUserFromUUID(stats.uuid)
        val time = System.currentTimeMillis()
        val uuid = stats.uuid
        val date = LocalDate.now().toString()
        val faceLink = if (stats.combined) user!!.member.effectiveAvatarUrl else "https://visage.surgeplay.com/face/512/$uuid?&time=$time"

        val builder = EmbedBuilder()
            .setAuthor(stats.name,"https://hypixel.net/player/$uuid",faceLink)
            .setThumbnail(faceLink)
            .setTitle("Hypixel stats", "https://hypixel.net/player/" + stats.uuid)
            .setColor(getColorFromScore(getHighestScore(stats.scores)))
            .addField("Wins", "`"+Utils.formatLine(stats.wins, stats.winsPosition)+"`",true)
            .addField("Walls", "`"+Utils.formatLine(stats.walls, stats.wallsPosition)+"`",true)
            .addBlankField(true)
            .addField("Qualification", "`"+Utils.formatLine(stats.qualification, stats.qualificationPosition)+"`",true)
            .addField("Finals", "`"+Utils.formatLine(stats.finals, stats.finalsPosition)+"`",true)
            .addBlankField(true)
            .addField("QF", "`"+Utils.formatLine(stats.total, stats.totalPosition)+"`",true)
            .addField("QF (according to the API)", "`"+Utils.formatLine(stats.totalAPI, stats.totalAPIPosition)+"`",true)
            .addBlankField(true)
            .addField("Challenges", "`"+Utils.formatLine(stats.challenges, stats.challengesPosition)+"`",true)

        if (stats.overall.toInt() != 0)
            builder.addField("Overall Rank", "`"+Utils.formatLine(stats.overall, stats.overallPosition)+"`",true)
        builder.addBlankField(true)
        builder.setFooter("$date | $uuid")

        return builder.build()
    }

    fun generateHypixelCompareEmbed(player1Data: String, player2Data: String): MessageEmbed {
        val player1 = HypixelStats(player1Data)
        val player2 = HypixelStats(player2Data)
        val date = LocalDate.now().toString()

        return EmbedBuilder()
            .setAuthor(player1.name + " VS " + player2.name,"https://hypixel.net/arcade-games/leaderboard/hole_in_the_wall", "https://www.blackoutburst.com/img/wally.png")
            .setTitle("Hypixel stats compare", "https://hypixel.net/arcade-games/leaderboard/hole_in_the_wall")
            .setColor(getColorFromScore(getHighestScore(player1.scores.plus(player2.scores))))
            .addField("**${player1.name}**",
            "```\n"+
                  comparisonLineLeft("Wins", player1.wins, player1.winsPosition)+
                  comparisonLineLeft("Walls", player1.walls, player1.wallsPosition)+
                  comparisonLineLeft("Qualification", player1.qualification, player1.qualificationPosition)+
                  comparisonLineLeft("Finals", player1.finals, player1.finalsPosition)+
                  comparisonLineLeft("Q/F", player1.total, player1.totalPosition)+
                  comparisonLineLeft("Q/F API", player1.totalAPI, player1.totalAPIPosition)+
                  comparisonLineLeft("Challenges", player1.challenges, player1.challengesPosition)+
                  if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) { comparisonLineLeft("Overall Rank", player1.overall, player1.overallPosition) } else { "" }+
                  "```",
                true)
            .addField("**[VS]**",
            "```\n"+
                  comparisonLineMiddle(player1.wins, player2.wins)+
                  comparisonLineMiddle(player1.walls, player2.walls)+
                  comparisonLineMiddle(player1.qualification, player2.qualification)+
                  comparisonLineMiddle(player1.finals, player2.finals)+
                  comparisonLineMiddle(player1.total, player2.total)+
                  comparisonLineMiddle(player1.totalAPI, player2.totalAPI)+
                  comparisonLineMiddle(player1.challenges, player2.challenges)+
                  if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) { comparisonLineMiddle(player1.overall, player2.overall) } else { "" }+
                  "```",
                true)
            .addField(player2.name,
            "```\n"+
                  comparisonLineRight("Wins", player2.wins, player2.winsPosition) +
                  comparisonLineRight("Walls", player2.walls, player2.wallsPosition)+
                  comparisonLineRight("Qualification", player2.qualification, player2.qualificationPosition)+
                  comparisonLineRight("Finals", player2.finals, player2.finalsPosition)+
                  comparisonLineRight("Q/F", player2.total, player2.totalPosition)+
                  comparisonLineRight("Q/F API", player2.totalAPI, player2.totalAPIPosition)+
                  comparisonLineRight("Challenges", player2.challenges, player2.challengesPosition)+
                  if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) { comparisonLineRight("Overall Rank", player2.overall, player2.overallPosition) } else { "" }+
                  "```"
                ,true)
            .setFooter(date)
            .build()
    }

    fun generatePracticeCompareEmbed(player1Data: String, player2Data: String): MessageEmbed {
        val player1 = PracticeStats(player1Data)
        val player2 = PracticeStats(player2Data)
        val date = LocalDate.now().toString()

        return EmbedBuilder()
            .setAuthor(player1.name + " VS " + player2.name,"https://hypixel.net/arcade-games/leaderboard/hole_in_the_wall", "https://www.blackoutburst.com/img/wally.png")
            .setTitle("Hypixel stats compare", "https://hypixel.net/arcade-games/leaderboard/hole_in_the_wall")
            .setColor(getColorFromScore(getHighestScore(player1.scores.plus(player2.scores))))
            .addField("**${player1.name}**",
                "```\n"+
                        comparisonLineLeft("Qualification", player1.qualification, player1.qualificationPosition)+
                        comparisonLineLeft("Finals", player1.finals, player1.finalsPosition)+
                        comparisonLineLeft("Lobby", player1.lobby, player1.lobbyPosition)+
                        comparisonLineLeft("Wide Qualification", player1.wideQualification, player1.wideQualificationPosition)+
                        comparisonLineLeft("Wide Finals", player1.wideFinals, player1.wideFinalsPosition)+
                        comparisonLineLeft("Total Score", player1.total, player1.totalPosition)+
                        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) { comparisonLineLeft("Overall Rank", player1.overall, player1.overallPosition) } else { "" }+
                        "```",
                true)
            .addField("**[VS]**",
                "```\n"+
                        comparisonLineMiddle(player1.qualification, player2.qualification)+
                        comparisonLineMiddle(player1.finals, player2.finals)+
                        comparisonLineMiddle(player1.lobby, player2.lobby)+
                        comparisonLineMiddle(player1.wideQualification, player2.wideQualification)+
                        comparisonLineMiddle(player1.wideFinals, player2.wideFinals)+
                        comparisonLineMiddle(player1.total, player2.total)+
                        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) { comparisonLineMiddle(player1.overall, player2.overall) } else { "" }+
                        "```",
                true)
            .addField(player2.name,
                "```\n"+
                        comparisonLineRight("Qualification", player2.qualification, player2.qualificationPosition) +
                        comparisonLineRight("Finals", player2.finals, player2.finalsPosition)+
                        comparisonLineRight("Lobby", player2.lobby, player2.lobbyPosition)+
                        comparisonLineRight("Wide Qualification", player2.wideQualification, player2.wideQualificationPosition)+
                        comparisonLineRight("Wide Finals", player2.wideFinals, player2.wideFinalsPosition)+
                        comparisonLineRight("Total Score", player2.total, player2.totalPosition)+
                        if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) { comparisonLineRight("Overall Rank", player2.overall, player2.overallPosition) } else { "" }+
                        "```"
                ,true)
            .setFooter(date)
            .build()
    }

    fun generateSkin(uuid: String, playerName: String): EmbedBuilder {
        val time = System.currentTimeMillis()
        return EmbedBuilder()
            .setAuthor(playerName,"https://fr.namemc.com/profile/$uuid","https://visage.surgeplay.com/face/512/$uuid?&time=$time")
            .setTitle("NameMC", "https://fr.namemc.com/profile/$uuid")
            .setColor(Color(71, 165, 255))
            .setImage("https://visage.surgeplay.com/full/512/$uuid?&time=$time")
    }

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
            Wally.logger.logStackTrace(e)
            null
        }
    }

    fun pbEmbed(stats :StatsObject, user :WallyUser, oldScore :Int, newScore :Int, gameType :String) : MessageEmbed {
        val time = System.currentTimeMillis()
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        val avatarUrl = user.member.user.avatarUrl

        val embed = EmbedBuilder()
            .setThumbnail("https://visage.surgeplay.com/full/512/${stats.uuid}?&time=$time")
            .setFooter("Discord ID: ${user.member.id}\nUUID: ${stats.uuid}")
            .setColor(getColorFromScore(newScore))
            .setAuthor("${user.member.effectiveName}#${user.member.user.discriminator} new $gameType PB !", wallyURL, avatarUrl)

        if (oldScore > 0) {
            embed.setTitle("${stats.name} improved ${Utils.getGenderPrefix(user.member.id)} **$gameType** Personal Best!")
            embed.addField("Old PB", "**$oldScore**",true)
            embed.addField("New PB", "**$newScore**",true)
            embed.addField("Increase","**${(newScore - oldScore)}**",true)
        } else {
            embed.setTitle("${stats.name} completed ${Utils.getGenderPrefix(user.member.id)} first game of **$gameType**!")
            embed.addField("They got", "**$newScore** points!",true)
        }

        return embed.build()
    }

    fun welcomeEmbed(stats: HypixelStats, user: WallyUser) : MessageEmbed {
        val time = System.currentTimeMillis()
        val url = "https://visage.surgeplay.com/face/512/${stats.uuid}?&time=$time"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        val avatarUrl = user.member.user.avatarUrl
        val name: String = user.member.effectiveName

        return EmbedBuilder()
            .setAuthor("$name | ${stats.name}", wallyURL, avatarUrl)
            .setThumbnail(url)
            .setFooter("Discord ID: ${user.member.id}\nUUID: ${stats.uuid}")
            .setTitle("$name was linked!")
            .setDescription("They have:")
            .addField("`${Wally.numberFormat.format(stats.wins)}` wins", "", true)
            .addField("`${Wally.numberFormat.format(stats.walls)}` walls", "", true)
            .addBlankField(true)
            .addField("`${Wally.numberFormat.format(stats.qualification)}` Q", "", true)
            .addField("`${Wally.numberFormat.format(stats.finals)}` F", "", true)
            .addBlankField(true)
            .addField("`${Wally.numberFormat.format(stats.total)}` Q/F", "", true)
            .addField("`${Wally.numberFormat.format(stats.totalAPI)}` Q/F (API)", "", true)
            .addBlankField(true)
            .addField("`${Wally.numberFormat.format(stats.challenges)}` Challenges", "", true)
            .setColor(getColorFromScore(getHighestScore(stats.scores)))
            .build()
    }
}