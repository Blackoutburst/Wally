package com.blackoutburst.wally.utils

import com.blackoutburst.wally.utils.EmbedUtils.generateLeaderboardEmbed
import com.blackoutburst.wally.utils.EmbedUtils.generatePracticeStatsEmbed
import com.blackoutburst.wally.utils.EmbedUtils.generateHypixelStatsEmbed
import com.blackoutburst.wally.utils.DatabaseUtils.isLinkedUUID
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserFromUUID
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.leaderboard.Leaderboard
import com.blackoutburst.wally.core.commands.DisplayPreference
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.commands.PermissionLevel
import java.util.UUID
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.utils.EmbedUtils.generateHypixelCompareEmbed
import com.blackoutburst.wally.utils.LineUtils.comparisonLine
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu
import java.io.File
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

object MessageUtils {

    fun sendLeaderboard(commandSender: WallyUser, message: Message, leaderboard: Leaderboard, page: Int, edit: Boolean) {
        if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generateLeaderboardMessage(leaderboard, page, true)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generateLeaderboardEmbed(leaderboard, page)).queue()
                else -> message.editMessage("Soon...").queue()
            }
        } else {
            val menu = createLeaderboardMenu(commandSender, page)
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.reply(generateLeaderboardMessage(leaderboard, page, true)).setActionRow(menu).queue()
                DisplayPreference.EMBED -> message.replyEmbeds(generateLeaderboardEmbed(leaderboard, page)).setActionRow(menu).queue()
                else -> message.reply("Soon...").setActionRow(menu).queue()
            }
        }
    }

    fun sendPracticeStats(commandSender: WallyUser, message: Message, data: String, uuid: String, edit: Boolean) {
        if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generatePracticeStatsMessage(data)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generatePracticeStatsEmbed(data)).queue()
                else -> message.editMessage("Soon...").queue()}
        } else {
            val menu = createAccountMenu(commandSender, uuid, "practice stats")

            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> menu?.let {
                    message.reply(generatePracticeStatsMessage(data)).setActionRow(it).queue()
                } ?: run {
                    message.reply(generatePracticeStatsMessage(data)).queue()
                }
                DisplayPreference.EMBED -> menu?.let {
                    message.replyEmbeds(generatePracticeStatsEmbed(data)).setActionRow(it).queue()
                } ?: run {
                    message.replyEmbeds(generatePracticeStatsEmbed(data)).queue()
                }
                else -> menu?.let {
                    message.reply("Soon...").setActionRow(it).queue()
                } ?: run {
                    message.reply("Soon...").queue()
                }
            }
        }
    }

    fun sendHypixelCompare(commandSender: WallyUser, message: Message, player1data: String, player2data: String) {
        when (commandSender.displayPreference) {
            DisplayPreference.TEXT -> message.reply(generateHypixelCompareMessage(player1data, player2data)).queue()
            DisplayPreference.EMBED -> message.replyEmbeds(generateHypixelCompareEmbed(player1data, player2data)).queue()
            else -> message.reply("Soon...").queue()
        }
    }

    fun sendHypixelStats(commandSender: WallyUser, message: Message, data: String, uuid: String, edit: Boolean) {
       if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generateHypixelStatsMessage(data)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generateHypixelStatsEmbed(data)).queue()
                else -> message.editMessage("Soon...").queue()
            }
        } else {
            val menu = createAccountMenu(commandSender, uuid, "stats")

            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> menu?.let {
                    message.reply(generateHypixelStatsMessage(data)).setActionRow(it).queue()
                } ?: run  {
                    message.reply(generateHypixelStatsMessage(data)).queue()
                }
                DisplayPreference.EMBED -> menu?.let {
                    message.replyEmbeds(generateHypixelStatsEmbed(data)).setActionRow(it).queue()
                } ?: run {
                    message.replyEmbeds(generateHypixelStatsEmbed(data)).queue()
                }
                else -> menu?.let {
                    message.reply("Soon...").setActionRow(it).queue()
                } ?: run {
                    message.reply("Soon...").queue()
                }
            }
        }
    }

    fun generateLeaderboardMessage(leaderboard: Leaderboard, page: Int, title: Boolean): String {
        val builder = StringBuilder()
        builder.append("```yml").append(System.lineSeparator())

        if (title) builder.append(leaderboard.name).append(System.lineSeparator()).append(System.lineSeparator())

        var leaderboardPage = (page - 1) * 10
        if (leaderboard.players.size <= leaderboardPage) {
            leaderboardPage = leaderboard.players.size - 10
        }

        for (i in leaderboardPage until leaderboardPage + 10) {
            if (leaderboard.players.size <= i) break
            val stats = leaderboard.players[i]

            builder.append("(#").append(i + 1).append(") ")
                .append(stats.name).append(": ")
                .append(
                    if (leaderboard.type == Leaderboard.Type.OVERALL) Wally.decimalFormat.format(stats.overall.toDouble()) else Wally.numberFormat.format(
                        stats.getDataFromType(leaderboard.type).toLong()
                    )
                )
                .append(System.lineSeparator())
        }
        builder.append("```")
        return builder.toString()
    }

    private fun generatePracticeStatsMessage(data: String): String {
        val stats = PracticeStats(data)

        return "```yml" + System.lineSeparator() +
                "Player name: " + stats.name + System.lineSeparator() + System.lineSeparator() +
                "Qualification: " + Utils.formatLine(stats.qualification, stats.qualificationPosition) + System.lineSeparator() +
                "Finals: " + Utils.formatLine(stats.finals, stats.finalsPosition) + System.lineSeparator() +
                "Lobby: " + Utils.formatLine(stats.lobby, stats.lobbyPosition) + System.lineSeparator() +
                "Wide Qualification: " + Utils.formatLine(stats.wideQualification, stats.wideQualificationPosition) + System.lineSeparator() +
                "Wide Finals: " + Utils.formatLine(stats.wideFinals, stats.wideFinalsPosition) + System.lineSeparator() +
                "Total Score: " + Utils.formatLine(stats.total, stats.totalPosition) + System.lineSeparator() +
                "Overall Rank: " + Utils.formatLine(stats.overall, stats.overallPosition) + System.lineSeparator() +
                "```"
    }

    private fun generateHypixelStatsMessage(data: String): String {
        val stats = HypixelStats(data)
        return "```yml" + System.lineSeparator() +
                "Player name: " + stats.name + System.lineSeparator() + System.lineSeparator() +
                "Wins: " + Utils.formatLine(stats.wins, stats.winsPosition) + System.lineSeparator() +
                "Walls: " + Utils.formatLine(stats.walls, stats.wallsPosition) + System.lineSeparator() +
                "Qualification: " + Utils.formatLine(stats.qualification, stats.qualificationPosition) + System.lineSeparator() +
                "Finals: " + Utils.formatLine(stats.finals, stats.finalsPosition) + System.lineSeparator() +
                "Q/F: " + Utils.formatLine(stats.total, stats.totalPosition) + System.lineSeparator() +
                "Q/F API: " + Utils.formatLine(stats.totalAPI, stats.totalAPIPosition) + System.lineSeparator() +
                "Challenges: " + Utils.formatLine(stats.challenges, stats.challengesPosition) + System.lineSeparator() +
                "Overall Rank: " + Utils.formatLine(stats.overall, stats.overallPosition) + System.lineSeparator() +
                "```"
    }

    private fun generateHypixelCompareMessage(player1Data: String, player2Data: String): String {
        val player1 = HypixelStats(player1Data)
        val player2 = HypixelStats(player2Data)

        return "```yml" + System.lineSeparator() +
                String.format("%-20s %-15s %-8s [VS] %8s %15s", "Player name:", player1.name, "", "", player2.name) + System.lineSeparator() + System.lineSeparator() +
                comparisonLine("Wins", player1.wins, player1.winsPosition, player2.wins, player2.winsPosition) +
                comparisonLine("Walls", player1.walls, player1.wallsPosition, player2.walls, player2.wallsPosition) +
                comparisonLine("Qualification", player1.qualification, player1.qualificationPosition, player2.qualification, player2.qualificationPosition) +
                comparisonLine("Finals", player1.finals, player1.finalsPosition, player2.finals, player2.finalsPosition) +
                comparisonLine("Q/F", player1.total, player1.totalPosition, player2.total, player2.totalPosition) +
                comparisonLine("Q/F API", player1.totalAPI, player1.totalAPIPosition, player2.totalAPI, player2.totalAPIPosition) +
                comparisonLine("Challenges", player1.challenges, player1.challengesPosition, player2.challenges, player2.challengesPosition) +
                comparisonLine("Overall Rank", player1.overall, player1.overallPosition, player2.overall, player2.overallPosition) +
                "```"
    }

    private fun createLeaderboardMenu(commandSender: WallyUser, page: Int): SelectMenu {
        val menu = SelectMenu.create(commandSender.member.id + ":leaderboard")
            .setRequiredRange(1, 1)
            .setPlaceholder("Select a leaderboard")
            .addOption(LeaderboardManager.winsLeaderboardHypixel.name, "0:$page")
            .addOption(LeaderboardManager.wallLeaderboardHypixel.name, "1:$page")
            .addOption(LeaderboardManager.qualificationLeaderboardHypixel.name, "2:$page")
            .addOption(LeaderboardManager.finalsLeaderboardHypixel.name, "3:$page")
            .addOption(LeaderboardManager.totalLeaderboardHypixel.name, "4:$page")
            .addOption(LeaderboardManager.totalApiLeaderboardHypixel.name, "5:$page")
            .addOption(LeaderboardManager.challengesLeaderboardHypixel.name, "6:$page")
            .addOption(LeaderboardManager.overallLeaderboardHypixel.name, "7:$page")
            .addOption(LeaderboardManager.qualificationLeaderboardPractice.name, "8:$page")
            .addOption(LeaderboardManager.finalsLeaderboardPractice.name, "9:$page")
            .addOption(LeaderboardManager.lobbyLeaderboardPractice.name, "10:$page")
            .addOption(LeaderboardManager.wideQualificationLeaderboardPractice.name, "11:$page")
            .addOption(LeaderboardManager.wideFinalsLeaderboardPractice.name, "12:$page")
            .addOption(LeaderboardManager.totalLeaderboardPractice.name, "13:$page")
            .addOption(LeaderboardManager.overallLeaderboardPractice.name, "14:$page")
        return menu.build()
    }

    fun createAccountMenu(commandSender: WallyUser, uuid: String, menuName: String): SelectMenu? {
        val linked = isLinkedUUID(uuid)
        if (!linked) return null

        val wallyUser = getWallyUserFromUUID(uuid)
        val memberId = wallyUser!!.member.id
        val folder = File("database/linked users/$memberId/accounts/")
        val entries = folder.list()
        if (entries == null || entries.size == 1) return null

        val menu = SelectMenu.create(commandSender.member.id + ":$menuName")
            .setRequiredRange(1, 1)
            .setPlaceholder("Select an account")
        for (s in entries) {
            val player = Mojang.getPlayerName(s!!) ?: continue
            menu.addOption(player, s)
        }
        return menu.build()
    }

    fun sendLogButtonReply(commandSender: WallyUser, message: Message, text: String) {
        if (commandSender.permissionLevel == PermissionLevel.ADMIN) {
            message.reply(text).setActionRow(
                Button.primary(commandSender.member.id + ":" + message.id, "Send me the log"),
                Button.danger(commandSender.member.id + ":" + UUID.randomUUID(), "Nevermind")
            ).queue { r: Message ->
                r.delete().queueAfter(
                    ConfigManager.loadValue("settings", "answer lifetime").toLong(),
                    TimeUnit.SECONDS
                )
            }
        } else {
            message.reply(text).queue()
        }
    }
}