package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.PermissionLevel
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

    fun generateCrossCompareEmbed(hypixelStats: HypixelStats, practiceStats: PracticeStats): MessageEmbed {
        val date = LocalDate.now().toString()

        return EmbedBuilder()
            .setAuthor(hypixelStats.name,"https://hypixel.net/arcade-games/leaderboard/hole_in_the_wall", "https://www.blackoutburst.com/img/wally.png")
            .setTitle("Hypixel VS Practice", "https://hypixel.net/arcade-games/leaderboard/hole_in_the_wall")
            .setColor(getColorFromScore(getHighestScore(hypixelStats.scores.plus(practiceStats.scores))))
            .addField("**Hypixel**",
                "```\n"+
                        comparisonLineLeft("Qualification", hypixelStats.qualification, hypixelStats.qualificationPosition)+
                        comparisonLineLeft("Finals", hypixelStats.finals, hypixelStats.finalsPosition)+
                        comparisonLineLeft("Q/F", hypixelStats.total, hypixelStats.totalPosition)+
                        if (hypixelStats.overall.toInt() != 0 && practiceStats.overall.toInt() != 0) { comparisonLineLeft("Overall Rank", hypixelStats.overall, hypixelStats.overallPosition) } else { "" }+
                        "```",
                true)
            .addField("**[VS]**",
                "```\n"+
                        comparisonLineMiddle(hypixelStats.qualification, practiceStats.qualification)+
                        comparisonLineMiddle(hypixelStats.finals, practiceStats.finals)+
                        comparisonLineMiddle(hypixelStats.total, practiceStats.total)+
                        if (hypixelStats.overall.toInt() != 0 && practiceStats.overall.toInt() != 0) { comparisonLineMiddle(hypixelStats.overall, practiceStats.overall) } else { "" }+
                        "```",
                true)
            .addField("Practice",
                "```\n"+
                        comparisonLineRight("Qualification", practiceStats.qualification, practiceStats.qualificationPosition)+
                        comparisonLineRight("Finals", practiceStats.finals, practiceStats.finalsPosition)+
                        comparisonLineRight("Q/F", practiceStats.total, practiceStats.totalPosition)+
                        if (hypixelStats.overall.toInt() != 0 && practiceStats.overall.toInt() != 0) { comparisonLineRight("Overall Rank", practiceStats.overall, practiceStats.overallPosition) } else { "" }+
                        "```"
                ,true)
            .setFooter(date)
            .build()
    }

    fun generateHypixelCompareEmbed(player1: HypixelStats, player2: HypixelStats): MessageEmbed {
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

    fun generatePracticeCompareEmbed(player1: PracticeStats, player2: PracticeStats): MessageEmbed {
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
            val fileReader = FileReader("config/changelog.txt")
            val reader = BufferedReader(fileReader)
            val changelog = StringBuilder()
            val title = reader.readLine()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                changelog.append(line).append("\n")
            }
            reader.close()
            fileReader.close()

            EmbedBuilder()
                .setAuthor(LocalDate.now().toString(), wallyURL, wallyPfp)
                .setTitle(title, wallyURL)
                .addField("Changes", changelog.toString(), false)
                .addField("", "[GitHub](https://github.com/Blackoutburst/Wally)", true)
                .addField("", "[Patreon](https://www.patreon.com/Blackoutburst)", true)
                .setColor(Color(71, 165, 255))
                .setFooter("Made with ❤️ by Blackoutburst", "https://www.blackoutburst.com/img/discord-pfp.png")
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

    fun adminCommandsPage1(): MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        var commands = "**Wally Admin commands:** (1/2)\n\n"
        commands += "`setLogLevel`\n"
        commands += "Set the logger message filter level\n\n"
        commands += "`reloadUser | reloadUsers`\n"
        commands += "Reload every wally users\n\n"
        commands += "`setPermissionLevel | setPerms | setPermission | spl` `[discordID | Minecraft name]`\n"
        commands += "Set a Wally user permission level\n\n"
        commands += "`getConfig | getConfiguration`\n"
        commands += "Retrieve Wally configuration file\n\n"
        commands += "`setConfig | setConfiguration`\n"
        commands += "Set Wally configuration file\n\n"
        commands += "`setTracker`\n"
        commands += "Set the Hypixel tracker in the channel used to run the command\n\n"
        commands += "`showTracker`\n"
        commands += "Show in which channel the Hypixel tracker is set\n\n"
        commands += "`setPracticeTracker`\n"
        commands += "Set the Practice tracker in the channel used to run the command\n\n"
        commands += "`showPracticeTracker`\n"
        commands += "Show in which channel the Practice tracker is set\n\n"
        commands += "`setAnswerLifetime | setAnswerLife` `[seconds]`\n"
        commands += "Set the life duration for certain types of messages\n\n"

        return EmbedBuilder()
            .setAuthor("Admin Commands (1/2)", wallyURL, iconURL)
            .setColor(Color(255, 0, 0))
            .setDescription(commands)
            .build()
    }

    fun adminCommandsPage2(): MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        var commands = "**Wally Admin commands:** (2/2)\n\n"
        commands += "`getLog | getLogs`\n"
        commands += "Retrieve the current log file\n\n"
        commands += "`preChangelog | previewChangelog`\n"
        commands += "Preview the changelog message\n\n"
        commands += "`changelog`\n"
        commands += "Display the changelog message inside the changelog channel\n\n"
        commands += "`getChangelog`\n"
        commands += "Get the changelog file\n\n"
        commands += "`setChangelog`\n"
        commands += "Set the changelog file\n\n"
        commands += "`logLevel | showLogLevel | displayLogLevel`\n"
        commands += "Display the current logger message filter level\n\n"
        commands += "`setPrefix | setCommandPrefix | scp` `[New prefix]`\n"
        commands += "Edit the command prefix\n\n"
        commands += "`sortLeaderboard | sortLead | sortLb | slb`\n"
        commands += "Sort the leaderboards\n\n"
        commands += "`setSubtitle | setSub | setTitle` `[discordID | Minecraft name] (Title)`\n"
        commands += "Change a Wally user title\n\n"
        commands += "`setDev` `[true | false]`\n"
        commands += "Define a Wally user as developer (no extra perms just a badge)\n\n"
        commands += "`setPatreonTier | setPatreon` `[0 | 1 | 2 | 3]`\n"
        commands += "Define a Wally user patreon tier\n"

        return EmbedBuilder()
            .setAuthor("Admin Commands (2/2)", wallyURL, iconURL)
            .setColor(Color(255, 0, 0))
            .setDescription(commands)
            .build()
    }

    fun staffCommandsPage1(): MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        var commands = "**Wally Staff commands:** (1/2)\n\n"
        commands += "`say` `[Message]`\n"
        commands += "Make wally speaks for you\n\n"
        commands += "`link` `[discordID] [Minecraft Name]`\n"
        commands += "Link a Minecraft account to a discord account\n\n"
        commands += "`unlink` `[discordID | Minecraft name]`\n"
        commands += "Unlink a Minecraft account from a discord account\n\n"
        commands += "`addAlt` `[discordID] [Minecraft Name]`\n"
        commands += "Add a Minecraft account to a discord account\n\n"
        commands += "`removeAlt | rmAlt` `[discordID | Minecraft name] [Alt Minecraft name]`\n"
        commands += "Remove a Minecraft account from a discord account\n\n"
        commands += "`listAccount | listAcc | listAccounts | listAccs` `[discordID | Minecraft name]`\n"
        commands += "Lists every Minecraft account related to a discord account\n\n"
        commands += "`excludeFromLeaderboard | excludeFromLb | excludeLb | elb` `[Minecraft Name]`\n"
        commands += "Exclude a Minecraft account from the leaderboard\n\n"
        commands += "`addToLeaderboard | addToLb | addLb | alb` `[Minecraft Name]`\n"
        commands += "Add a Minecraft account to the leaderboard\n\n"
        commands += "`excludeList | eList`\n"
        commands += "Display every account excluded from the leaderboard\n\n"

        return EmbedBuilder()
            .setAuthor("Staff Commands (1/2)", wallyURL, iconURL)
            .setColor(Color(255, 128, 0))
            .setDescription(commands)
            .build()
    }

    fun staffCommandsPage2(): MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        var commands = "**Wally Staff commands:** (2/2)\n\n"
        commands += "`setMain | setMainAccount | setMainAcc` `[discordID | Minecraft name] [Minecraft Name]`\n"
        commands += "Change a Wally user main account\n\n"
        commands += "`deleteFontColor | dfc` `[discordID | Minecraft name]`\n"
        commands += "Reset someone custom font color\n\n"
        commands += "`deleteFont | df` `[discordID | Minecraft name]`\n"
        commands += "Remove specified user custom font\n\n"
        commands += "`deleteFont | df` `[discordID | Minecraft name]`\n"
        commands += "Remove specified user custom font\n\n"
        commands += "`deleteBackground | deleteBg | dbg` `[discordID | Minecraft name]`\n"
        commands += "Remove specified user custom background\n\n"
        commands += "`deleteFontSize | dfs` `[discordID | Minecraft name]`\n"
        commands += "Reset someone custom font size\n\n"
        commands += "`fakePB` `[discordID | Minecraft name] [GameType] [Old score] [New score]`\n"
        commands += "Display a fake Hypixel PB\n\n"
        commands += "`fakePracticePB` `[discordID | Minecraft name] [GameType] [Old score] [New score]`\n"
        commands += "Display a fake Practice PB\n\n"
        commands += "`forcePB | fpb` `[discordID | Minecraft name]`\n"
        commands += "Force Wally to check if the specified user made a new PB (Hypixel/Practice)\n\n"

        return EmbedBuilder()
            .setAuthor("Staff Commands (2/2)", wallyURL, iconURL)
            .setColor(Color(255, 128, 0))
            .setDescription(commands)
            .build()
    }

    fun generatePGraphEmbed(data: String, type: String, typeName: String) : MessageEmbed {
        var average = 0

        val history = APIUtils.getValuesFromAPI(data, type, "[0,0]").removeSurrounding("[", "]").split(",").map { it.toInt() }
        var validGames = 1
        for (i in 0 until history.size) {
            if (history[i] != 0) {
                average += history[i]
                validGames++
            }
        }
        average /= validGames

        var validHistory = history

        validHistory = validHistory.filter { it >= (average - 50) }

        CanvasUtils.createPGraphImage(validHistory.toIntArray(), validHistory.size)

        val stats = PracticeStats(data)

        return EmbedBuilder()
            .setAuthor("${stats.name} | $typeName - latest 100 games","https://namemc.com/profile/${stats.uuid}","https://www.blackoutburst.com/img/wally.png")
            .setTitle("Average Score: $average")
            .setThumbnail("https://visage.surgeplay.com/face/128/${stats.uuid}")
            .setColor(getColorFromScore(average))
            .setImage("attachment://graph.png")
            .build()
    }

    fun hypixelCommands(): MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        var commands = "**Wally Hypixel commands:**\n\n"
        commands += "`leaderboard | leaderboards | lead | leads | lb | lbs` `(page)`\n"
        commands += "Display the Hole in the Wall leaderboards\n\n"
        commands += "`stats | stat | s` `(Minecraft name)`\n"
        commands += "Show a player stats\n\n"
        commands += "`compare | c` `(Minecraft name) [Minecraft name]`\n"
        commands += "Compare two player stats\n\n"
        commands += "`crossCompare | cc` `(Minecraft name)`\n"
        commands += "Cross compare a player Hypixel and Practice stats\n\n"
        commands += "`convert` `[score] [Q | F]`\n"
        commands += "Convert a Qualification score to Finals or Finals to Qualification\n\n"


        return EmbedBuilder()
            .setAuthor("Hypixel Commands", wallyURL, iconURL)
            .setColor(Color(71, 165, 255))
            .setDescription(commands)
            .build()
    }

    fun practiceCommands(): MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        var commands = "**Wally Practice commands:**\n\n"
        commands += "`leaderboard | leaderboards | lead | leads | lb | lbs` `(page)`\n"
        commands += "Display the Hole in the Wall leaderboards\n\n"
        commands += "`practiceStats | practiceStat | ps | pStats | pStat` `(Minecraft name)`\n"
        commands += "Show a player stats\n\n"
        commands += "`pCompare | pc | practiceCompare | practiceC` `(Minecraft name) [Minecraft name]`\n"
        commands += "Compare two player stats\n\n"
        commands += "`crossCompare | cc` `(Minecraft name)`\n"
        commands += "Cross compare a player Hypixel and Practice stats\n\n"
        commands += "`pGraph | practiceGraph` `(Minecraft name)`\n"
        commands += "Display a graph of the latest 100 games on the practice server\n\n"


        return EmbedBuilder()
            .setAuthor("Practice Commands", wallyURL, iconURL)
            .setColor(Color(71, 255, 0))
            .setDescription(commands)
            .build()
    }

    fun miscCommandsPage1(): MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        var commands = "**Wally Miscellaneous commands:** (1/2)\n\n"
        commands += "`ping`\n"
        commands += "Show various Wally data\n\n"
        commands += "`poll` `[message]`\n"
        commands += "Create a basic poll\n\n"
        commands += "`skin` `[Minecraft name]`\n"
        commands += "Display someone Minecraft skin\n\n"
        commands += "`setDisplayPreference | sdp`\n"
        commands += "Changes the way certain commands looks\n\n"
        commands += "`setFontColor | sfc` `[color in hexadecimal]`\n"
        commands += "Changes your stats font color\n\n"
        commands += "`resetFontColor | rfc`\n"
        commands += "Reset your stats font color\n\n"
        commands += "`setFont | sf` `[Upload a .TTF or .OTF file with the command]`\n"
        commands += "Set your stats font\n\n"
        commands += "`resetFont | rf`\n"
        commands += "Reset your stats font\n\n"
        commands += "`setFontSize | sfs` [size]\n"
        commands += "Set your stats font size\n\n\n"
        commands += "`resetFontSize | rfs`\n"
        commands += "Reset your stats font size\n\n"

        return EmbedBuilder()
            .setAuthor("Miscellaneous Commands (1/2)", wallyURL, iconURL)
            .setColor(Color(255, 150, 255))
            .setDescription(commands)
            .build()
    }

    fun miscCommandsPage2(): MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        var commands = "**Wally Miscellaneous commands:** (2/2)\n\n"

        commands += "`setBackground | setBg | sbg` `[Upload a .PNG, .JPG, .JPEG file with the command]`\n"
        commands += "Update your stats background\n\n"
        commands += "`resetBackground | resetBg | rbg`\n"
        commands += "Reset your stats background\n\n"
        commands += "`resetBackground | resetBg | rbg`\n"
        commands += "Reset your stats background\n\n"
        commands += "`toggleHeader | th`\n"
        commands += "Toggle your stats header\n\n"
        commands += "`suggestion | suggest`\n"
        commands += "Create a suggestion for Wally, the practice server or even the discord server\n\n"
        commands += "`who | whois`\n"
        commands += "Display various information about a player\n\n"
        commands += "`help`\n"
        commands += "Display Wally commands\n\n"

        return EmbedBuilder()
            .setAuthor("Miscellaneous Commands (2/2)", wallyURL, iconURL)
            .setColor(Color(255, 150, 255))
            .setDescription(commands)
            .build()
    }

    fun helpEmbed(commandSender :WallyUser) : MessageEmbed {
        val iconURL = "https://www.blackoutburst.com/img/wally.png"
        val wallyURL = "https://github.com/Blackoutburst/Wally"
        val embed: EmbedBuilder = EmbedBuilder()
            .setAuthor("Commands", wallyURL, iconURL)
            .setColor(Color(71, 165, 255))
            .setDescription("This is wally commands categories.\nUse `help [categories]` to display actual commands.\nOr use one of the buttons below!")

        if (commandSender.permissionLevel.weight >= PermissionLevel.ADMIN.weight) {
            embed.addField("**Admin**", "Display Admin commands", true)
        }

        if (commandSender.permissionLevel.weight >= PermissionLevel.STAFF.weight) {
            embed.addField("**Staff**", "Display Staff commands", true)
        }
        embed.addField("**Hypixel**", "Display Hypixel commands", true)
        embed.addField("**Practice**", "Display Practice Server commands", true)
        embed.addField("**Misc**", "Display Miscellaneous commands", true)

        return embed.build()
    }
}