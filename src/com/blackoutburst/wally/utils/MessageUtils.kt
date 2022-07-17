package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.WallyUser
import com.blackoutburst.wally.core.commands.DisplayPreference
import com.blackoutburst.wally.core.commands.PermissionLevel
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.leaderboard.Leaderboard
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import com.blackoutburst.wally.core.request.Mojang
import com.blackoutburst.wally.core.statsobject.HypixelStats
import com.blackoutburst.wally.core.statsobject.PracticeStats
import com.blackoutburst.wally.utils.DatabaseUtils.getWallyUserFromUUID
import com.blackoutburst.wally.utils.DatabaseUtils.isLinkedUUID
import com.blackoutburst.wally.utils.EmbedUtils.generateCrossCompareEmbed
import com.blackoutburst.wally.utils.EmbedUtils.generateHypixelCompareEmbed
import com.blackoutburst.wally.utils.EmbedUtils.generateHypixelStatsEmbed
import com.blackoutburst.wally.utils.EmbedUtils.generateLeaderboardEmbed
import com.blackoutburst.wally.utils.EmbedUtils.generatePGraphEmbed
import com.blackoutburst.wally.utils.EmbedUtils.generatePracticeCompareEmbed
import com.blackoutburst.wally.utils.EmbedUtils.generatePracticeStatsEmbed
import com.blackoutburst.wally.utils.LineUtils.comparisonLine
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

object MessageUtils {

    fun sendLeaderboard(commandSender: WallyUser, message: Message, leaderboard: Leaderboard, page: Int, edit: Boolean) {
        if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generateLeaderboardMessage(leaderboard, page, true)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generateLeaderboardEmbed(leaderboard, page)).queue()
                else -> {
                    CanvasUtils.createLeaderboardCanvas(leaderboard, page-1)
                    message.editMessage("\u00A0").retainFiles(message.attachments.subList(1, message.attachments.size)).addFile(File("leaderboard.png")).queue()
                }
            }
        } else {
            val menu = createLeaderboardMenu(commandSender, page)
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.reply(generateLeaderboardMessage(leaderboard, page, true)).setActionRow(menu).queue()
                DisplayPreference.EMBED -> message.replyEmbeds(generateLeaderboardEmbed(leaderboard, page)).setActionRow(menu).queue()
                else -> {
                    CanvasUtils.createLeaderboardCanvas(leaderboard, page-1)
                    message.reply("\u00A0").addFile(File("leaderboard.png")).setActionRow(menu).queue()
                }
            }
        }
    }

    fun sendPGraph(commandSender: WallyUser, data: String, message: Message, type: String, typeName: String, edit: Boolean) {
        if (edit) {
            val menu = pGraphMenu(commandSender, PracticeStats(data).uuid)
            message.editMessageEmbeds(generatePGraphEmbed(data, type, typeName)).override(true).addFile(File("pgraph.png"), "graph.png").setActionRow(menu).queue()
        } else {
            val menu = pGraphMenu(commandSender, PracticeStats(data).uuid)
            message.replyEmbeds(generatePGraphEmbed(data, type, typeName)).addFile(File("pgraph.png"), "graph.png").setActionRow(menu).queue()
        }
    }

    fun sendPracticeStats(commandSender: WallyUser, message: Message, stats: PracticeStats, uuid: String, edit: Boolean) {
        if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generatePracticeStatsMessage(stats)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generatePracticeStatsEmbed(stats)).queue()
                else -> {
                    CanvasUtils.createPracticeStatsCanvas(stats)
                    message.editMessage("\u00A0").retainFiles(message.attachments.subList(1, message.attachments.size)).addFile(File("practice_stats.png")).queue()
                }
            }
        } else {
            val menu = createAccountMenu(commandSender, uuid, "practice stats")

            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> menu?.let {
                    message.reply(generatePracticeStatsMessage(stats)).setActionRow(it).queue()
                } ?: run {
                    message.reply(generatePracticeStatsMessage(stats)).queue()
                }
                DisplayPreference.EMBED -> menu?.let {
                    message.replyEmbeds(generatePracticeStatsEmbed(stats)).setActionRow(it).queue()
                } ?: run {
                    message.replyEmbeds(generatePracticeStatsEmbed(stats)).queue()
                }
                else -> menu?.let {
                    CanvasUtils.createPracticeStatsCanvas(stats)
                    message.reply("\u00A0").addFile(File("practice_stats.png")).setActionRow(it).queue()
                } ?: run {
                    CanvasUtils.createPracticeStatsCanvas(stats)
                    message.reply("\u00A0").addFile(File("practice_stats.png")).queue()
                }
            }
        }
    }

    fun sendHypixelCompare(commandSender: WallyUser, message: Message, player1data: HypixelStats, player2data: HypixelStats, edit: Boolean) {
        if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generateHypixelCompareMessage(player1data, player2data)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generateHypixelCompareEmbed(player1data, player2data)).queue()
                else -> {
                    CanvasUtils.createHypixelCompareCanvas(player1data, player2data)
                    message.editMessage("\u00A0").retainFiles(message.attachments.subList(1, message.attachments.size)).addFile(File("compare.png")).queue()
                }
            }
        } else {
            val menu = createCompareAccountMenu(commandSender, player1data.uuid, player2data.uuid,"compare")

            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> menu?.let {
                    message.reply(generateHypixelCompareMessage(player1data, player2data)).setActionRow(it).queue()
                } ?: run  {
                    message.reply(generateHypixelCompareMessage(player1data, player2data)).queue()
                }
                DisplayPreference.EMBED -> menu?.let {
                    message.replyEmbeds(generateHypixelCompareEmbed(player1data, player2data)).setActionRow(it).queue()
                } ?: run {
                    message.replyEmbeds(generateHypixelCompareEmbed(player1data, player2data)).queue()
                }
                else -> menu?.let {
                    CanvasUtils.createHypixelCompareCanvas(player1data, player2data)
                    message.reply("\u00A0").addFile(File("compare.png")).setActionRow(it).queue()
                } ?: run {
                    CanvasUtils.createHypixelCompareCanvas(player1data, player2data)
                    message.reply("\u00A0").addFile(File("compare.png")).queue()
                }
            }
        }
    }

    fun sendPracticeCompare(commandSender: WallyUser, message: Message, player1data: PracticeStats, player2data: PracticeStats, edit: Boolean) {
        if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generatePracticeCompareMessage(player1data, player2data)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generatePracticeCompareEmbed(player1data, player2data)).queue()
                else -> {
                    CanvasUtils.createPracticeCompareCanvas(player1data, player2data)
                    message.editMessage("\u00A0").retainFiles(message.attachments.subList(1, message.attachments.size)).addFile(File("practice_compare.png")).queue()
                }
            }
        } else {
            val menu = createCompareAccountMenu(commandSender, player1data.uuid, player2data.uuid,"practice compare")

            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> menu?.let {
                    message.reply(generatePracticeCompareMessage(player1data, player2data)).setActionRow(it).queue()
                } ?: run  {
                    message.reply(generatePracticeCompareMessage(player1data, player2data)).queue()
                }
                DisplayPreference.EMBED -> menu?.let {
                    message.replyEmbeds(generatePracticeCompareEmbed(player1data, player2data)).setActionRow(it).queue()
                } ?: run {
                    message.replyEmbeds(generatePracticeCompareEmbed(player1data, player2data)).queue()
                }
                else -> menu?.let {
                    CanvasUtils.createPracticeCompareCanvas(player1data, player2data)
                    message.reply("\u00A0").addFile(File("practice_compare.png")).setActionRow(it).queue()
                } ?: run {
                    CanvasUtils.createPracticeCompareCanvas(player1data, player2data)
                    message.reply("\u00A0").addFile(File("practice_compare.png")).queue()
                }
            }
        }
    }

    fun sendCrossCompare(commandSender: WallyUser, message: Message, stats: HypixelStats, practiceStats: PracticeStats, uuid: String, edit: Boolean) {
        if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generateCrossCompareMessage(stats, practiceStats)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generateCrossCompareEmbed(stats, practiceStats)).queue()
                else -> {
                    CanvasUtils.createCrossCompareCanvas(stats, practiceStats)
                    message.editMessage("\u00A0").retainFiles(message.attachments.subList(1, message.attachments.size)).addFile(File("cross_compare.png")).queue()
                }
            }
        } else {
            val menu = createAccountMenu(commandSender, uuid, "cross compare", false)

            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> menu?.let {
                    message.reply(generateCrossCompareMessage(stats, practiceStats)).setActionRow(it).queue()
                } ?: run  {
                    message.reply(generateCrossCompareMessage(stats, practiceStats)).queue()
                }
                DisplayPreference.EMBED -> menu?.let {
                    message.replyEmbeds(generateCrossCompareEmbed(stats, practiceStats)).setActionRow(it).queue()
                } ?: run {
                    message.replyEmbeds(generateCrossCompareEmbed(stats, practiceStats)).queue()
                }
                else -> menu?.let {
                    CanvasUtils.createCrossCompareCanvas(stats, practiceStats)
                    message.reply("\u00A0").addFile(File("cross_compare.png")).setActionRow(it).queue()
                } ?: run {
                    CanvasUtils.createCrossCompareCanvas(stats, practiceStats)
                    message.reply("\u00A0").addFile(File("cross_compare.png")).queue()
                }
            }
        }
    }

    fun sendHypixelStats(commandSender: WallyUser, message: Message, stats: HypixelStats, uuid: String, edit: Boolean) {
       if (edit) {
            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> message.editMessage(generateHypixelStatsMessage(stats)).queue()
                DisplayPreference.EMBED -> message.editMessageEmbeds(generateHypixelStatsEmbed(stats)).queue()
                else -> {
                    CanvasUtils.createHypixelStatsCanvas(stats)
                    message.editMessage("\u00A0").retainFiles(message.attachments.subList(1, message.attachments.size)).addFile(File("stats.png")).queue()
                }
            }
        } else {
            val menu = createAccountMenu(commandSender, uuid, "stats")

            when (commandSender.displayPreference) {
                DisplayPreference.TEXT -> menu?.let {
                    message.reply(generateHypixelStatsMessage(stats)).setActionRow(it).queue()
                } ?: run  {
                    message.reply(generateHypixelStatsMessage(stats)).queue()
                }
                DisplayPreference.EMBED -> menu?.let {
                    message.replyEmbeds(generateHypixelStatsEmbed(stats)).setActionRow(it).queue()
                } ?: run {
                    message.replyEmbeds(generateHypixelStatsEmbed(stats)).queue()
                }
                else -> menu?.let {
                    CanvasUtils.createHypixelStatsCanvas(stats)
                    message.reply("\u00A0").addFile(File("stats.png")).setActionRow(it).queue()
                } ?: run {
                    CanvasUtils.createHypixelStatsCanvas(stats)
                    message.reply("\u00A0").addFile(File("stats.png")).queue()
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
                    if (leaderboard.type == Leaderboard.Type.OVERALL) Wally.decimalFormat.format(stats.overall) else Wally.numberFormat.format(
                        stats.getDataFromType(leaderboard.type).toLong()
                    )
                )
                .append(System.lineSeparator())
        }
        builder.append("```")
        return builder.toString()
    }

    private fun generatePracticeStatsMessage(stats: PracticeStats): String {
        return "```yml" + System.lineSeparator() +
                "Player name: " + stats.name + System.lineSeparator() + System.lineSeparator() +
                "Qualification: " + Utils.formatLine(stats.qualification, stats.qualificationPosition) + System.lineSeparator() +
                "Finals: " + Utils.formatLine(stats.finals, stats.finalsPosition) + System.lineSeparator() +
                "Lobby: " + Utils.formatLine(stats.lobby, stats.lobbyPosition) + System.lineSeparator() +
                "Wide Qualification: " + Utils.formatLine(stats.wideQualification, stats.wideQualificationPosition) + System.lineSeparator() +
                "Wide Finals: " + Utils.formatLine(stats.wideFinals, stats.wideFinalsPosition) + System.lineSeparator() +
                "Total Score: " + Utils.formatLine(stats.total, stats.totalPosition) + System.lineSeparator() +
                if (stats.overall.toInt() != 0) { "Overall Rank: " + Utils.formatLine(stats.overall, stats.overallPosition) } else { "" } + System.lineSeparator() +
                "```"
    }

    private fun generateHypixelStatsMessage(stats: HypixelStats): String {
        return "```yml" + System.lineSeparator() +
                "Player name: " + stats.name + System.lineSeparator() + System.lineSeparator() +
                "Wins: " + Utils.formatLine(stats.wins, stats.winsPosition) + System.lineSeparator() +
                "Walls: " + Utils.formatLine(stats.walls, stats.wallsPosition) + System.lineSeparator() +
                "Qualification: " + Utils.formatLine(stats.qualification, stats.qualificationPosition) + System.lineSeparator() +
                "Finals: " + Utils.formatLine(stats.finals, stats.finalsPosition) + System.lineSeparator() +
                "Q/F: " + Utils.formatLine(stats.total, stats.totalPosition) + System.lineSeparator() +
                "Q/F API: " + Utils.formatLine(stats.totalAPI, stats.totalAPIPosition) + System.lineSeparator() +
                "Challenges: " + Utils.formatLine(stats.challenges, stats.challengesPosition) + System.lineSeparator() +
                if (stats.overall.toInt() != 0) { "Overall Rank: " + Utils.formatLine(stats.overall, stats.overallPosition) } else { "" } + System.lineSeparator() +
                "```"
    }

    private fun generateCrossCompareMessage(hypixelStats: HypixelStats, practiceStats: PracticeStats): String {
        return "```yml" + System.lineSeparator() +
                String.format("%-20s %-15s %-8s [VS] %8s %15s", "${hypixelStats.name}:", "Hypixel", "", "", "Practice") + System.lineSeparator() + System.lineSeparator() +
                comparisonLine("Qualification", hypixelStats.qualification, hypixelStats.qualificationPosition, practiceStats.qualification, practiceStats.qualificationPosition) +
                comparisonLine("Finals", hypixelStats.finals, hypixelStats.finalsPosition, practiceStats.finals, practiceStats.finalsPosition) +
                comparisonLine("Q/F", hypixelStats.total, hypixelStats.totalPosition, practiceStats.total, practiceStats.totalPosition) +
                if (hypixelStats.overall.toInt() != 0 && practiceStats.overall.toInt() != 0) { comparisonLine("Overall Rank", hypixelStats.overall, hypixelStats.overallPosition, practiceStats.overall, practiceStats.overallPosition) } else { "" } + System.lineSeparator() +
                "```"
    }

    private fun generateHypixelCompareMessage(player1: HypixelStats, player2: HypixelStats): String {

        return "```yml" + System.lineSeparator() +
                String.format("%-20s %-15s %-8s [VS] %8s %15s", "Player name:", player1.name, "", "", player2.name) + System.lineSeparator() + System.lineSeparator() +
                comparisonLine("Wins", player1.wins, player1.winsPosition, player2.wins, player2.winsPosition) +
                comparisonLine("Walls", player1.walls, player1.wallsPosition, player2.walls, player2.wallsPosition) +
                comparisonLine("Qualification", player1.qualification, player1.qualificationPosition, player2.qualification, player2.qualificationPosition) +
                comparisonLine("Finals", player1.finals, player1.finalsPosition, player2.finals, player2.finalsPosition) +
                comparisonLine("Q/F", player1.total, player1.totalPosition, player2.total, player2.totalPosition) +
                comparisonLine("Q/F API", player1.totalAPI, player1.totalAPIPosition, player2.totalAPI, player2.totalAPIPosition) +
                comparisonLine("Challenges", player1.challenges, player1.challengesPosition, player2.challenges, player2.challengesPosition) +
                if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) { comparisonLine("Overall Rank", player1.overall, player1.overallPosition, player2.overall, player2.overallPosition) } else { "" } + System.lineSeparator() +
                "```"
    }

    private fun generatePracticeCompareMessage(player1: PracticeStats, player2: PracticeStats): String {

        return "```yml" + System.lineSeparator() +
                String.format("%-20s %-15s %-8s [VS] %8s %15s", "Player name:", player1.name, "", "", player2.name) + System.lineSeparator() + System.lineSeparator() +
                comparisonLine("Qualification", player1.qualification, player1.qualificationPosition, player2.qualification, player2.qualificationPosition) +
                comparisonLine("Finals", player1.finals, player1.finalsPosition, player2.finals, player2.finalsPosition) +
                comparisonLine("Lobby", player1.lobby, player1.lobbyPosition, player2.lobby, player2.lobbyPosition) +
                comparisonLine("Wide Qualification", player1.wideQualification, player1.wideQualification, player2.wideQualification, player2.wideQualificationPosition) +
                comparisonLine("Wide Finals", player1.wideFinals, player1.wideFinalsPosition, player2.wideFinals, player2.wideFinalsPosition) +
                comparisonLine("Total Score", player1.total, player1.totalPosition, player2.total, player2.totalPosition) +
                if (player1.overall.toInt() != 0 && player2.overall.toInt() != 0) { comparisonLine("Overall Rank", player1.overall, player1.overallPosition, player2.overall, player2.overallPosition) } else { "" } + System.lineSeparator() +
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

    private fun pGraphMenu(commandSender: WallyUser, uuid: String): SelectMenu {
        val menu = SelectMenu.create(commandSender.member.id + ":practice graph")
            .setRequiredRange(1, 1)
            .setPlaceholder("Select a game type")
            .addOption("Qualification", "qualification_history:$uuid")
            .addOption("Finals", "finals_history:$uuid")
            .addOption("Lobby", "lobby_history:$uuid")
            .addOption("Wide Qualification", "wide_qualification_history:$uuid")
            .addOption("Wide Finals", "wide_finals_history:$uuid")
        return menu.build()
    }

    fun createAccountMenu(commandSender: WallyUser, uuid: String, menuName: String, includeCombined :Boolean = true): SelectMenu? {
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

        if (includeCombined) {
            menu.addOption("Combined stats", "all")
        }

        return menu.build()
    }

    fun createCompareAccountMenu(commandSender: WallyUser, uuid: String, uuid2: String, menuName: String, includeCombined :Boolean = true): SelectMenu? {
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


        var combined = ""
        for (s in entries) {
            val player = Mojang.getPlayerName(s!!) ?: continue
            menu.addOption(player, "$s:$uuid2")
            combined += "$s:"
        }

        if (includeCombined) {
            menu.addOption("Combined stats", "$combined$uuid2")
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