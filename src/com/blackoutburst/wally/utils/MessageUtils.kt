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
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu
import java.io.File
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

object MessageUtils {

    /**
     * Concat every command args into a single string
     * @param message the message
     */
    fun concatArgs(message: Message): String {
        val tmp = message.contentRaw.trim { it <= ' ' }.split(" ").toTypedArray()
        val words = listOf(*tmp.copyOfRange(1, tmp.size))
        val builder = StringBuilder()

        words.forEach(Consumer { w: String? -> builder.append(w).append(' ') })
        return builder.toString()
    }

    /**
     * Send the leaderboard message
     * @param commandSender the command sender
     * @param message the message
     * @param leaderboard the leaderboard
     * @param page the leaderboard page
     * @param edit if the message should be edited instead of send
     */
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

    /**
     * Send the stats message
     * @param commandSender the command sender
     * @param message the message
     * @param data the api data
     * @param uuid the player uuid
     * @param edit if the message should be edited instead of send
     */
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

    /**
     * Send the stats message
     * @param commandSender the command sender
     * @param message the message
     * @param data the api data
     * @param uuid the player uuid
     * @param edit if the message should be edited instead of send
     */
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

    /**
     * Create text with leaderboard information inside
     * @param leaderboard the leaderboard
     * @param page the leaderboard page
     * @return a message containing leaderboard page information
     */
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

    /**
     * Create a text with Practice stats information inside
     * @param data a valid Practice json data
     * @return a message containing every hole in the wall stats
     */
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

    /**
     * Create a text with Hypixel stats information inside
     * @param data a valid Hypixel json data
     * @return a message containing every hole in the wall stats
     */
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

    /**
     * Create a select menu to switch between a leaderboards
     * @param commandSender the command sender
     * @return a select menu ready to be used on a message
     */
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

    /**
     * Create a select menu to switch between a user accounts
     * @param commandSender the command sender
     * @param uuid the player uuid
     * @param menuName the menu name
     * @return a select menu ready to be used on a message
     */
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

    /**
     * Send an error reply,
     * if the user who generated the error message
     * does have the ADMIN permission level, extra button will be generated
     * to send the log file or cancel the action
     *
     * @param commandSender the user who generated the error
     * @param message the discord message sent by the user
     * @param text the text used in the reply
     */
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

    /**
     * Retrieve a message from a message channel history
     *
     * @param messageChannel the message channel
     * @param messageID the message id
     * @param historyCount the number of message we need to go through
     * @return the message if found null otherwise
     */
    fun getMessageFromHistory(messageChannel: MessageChannel,messageID: String, historyCount: Int): Message? {
        val history = messageChannel.history.retrievePast(historyCount).complete()
        val message = history.stream().filter { msg: Message? -> msg!!.id == messageID }
            .findFirst()
        return message.orElse(null)
    }
}