package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.leaderboard.Leaderboard;
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.PracticeStats;
import com.blackoutburst.wally.core.statsobject.StatsObject;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MessageUtils {

    /**
     * Send the leaderboard message
     * @param commandSender the command sender
     * @param message the message
     * @param leaderboard the leaderboard
     * @param page the leaderboard page
     * @param edit if the message should be edited instead of send
     */
    public static void sendLeaderboard(@Nonnull WallyUser commandSender, @Nonnull Message message, @Nonnull Leaderboard leaderboard, int page, boolean edit) {
        if (edit) {
            switch (commandSender.getDisplayPreference()) {
                case TEXT:
                    message.editMessage(generateLeaderboardMessage(leaderboard, page)).queue();
                break;
                case EMBED:
                 //   message.editMessageEmbeds(EmbedUtils.generateHypixelStatsEmbed(leaderboard, page)).queue();
                break;
                default:
                    message.editMessage("Soon...").queue();
                break;
            }
        } else {
            final SelectMenu menu = createLeaderboardMenu(commandSender, "leaderboard", page);

            switch (commandSender.getDisplayPreference()) {
                case TEXT:
                    message.reply(generateLeaderboardMessage(leaderboard, page)).setActionRow(menu).queue();
                break;
                case EMBED:
                    //message.replyEmbeds(EmbedUtils.generateHypixelStatsEmbed(leaderboard, page)).setActionRow(menu).queue();
                break;
                default:
                    message.reply("Soon...").setActionRow(menu).queue();
                break;
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
    public static void sendPracticeStats(@Nonnull WallyUser commandSender, @Nonnull Message message, @Nonnull String data, @Nonnull String uuid, boolean edit) {
        if (edit) {
            switch (commandSender.getDisplayPreference()) {
                case TEXT:
                    message.editMessage(generatePracticeStatsMessage(data)).queue();
                    break;
                case EMBED:
                    message.editMessageEmbeds(EmbedUtils.generatePracticeStatsEmbed(data)).queue();
                    break;
                default:
                    message.editMessage("Soon...").queue();
                    break;
            }
        } else {
            final SelectMenu menu = createAccountMenu(commandSender, uuid, "practice stats");

            switch (commandSender.getDisplayPreference()) {
                case TEXT:
                    if (menu != null) {
                        message.reply(generatePracticeStatsMessage(data)).setActionRow(menu).queue();
                    } else {
                        message.reply(generatePracticeStatsMessage(data)).queue();
                    }
                    break;
                case EMBED:
                    if (menu != null) {
                        message.replyEmbeds(EmbedUtils.generatePracticeStatsEmbed(data)).setActionRow(menu).queue();
                    } else {
                        message.replyEmbeds(EmbedUtils.generatePracticeStatsEmbed(data)).queue();
                    }
                    break;
                default:
                    if (menu != null) {
                        message.reply("Soon...").setActionRow(menu).queue();
                    } else {
                        message.reply("Soon...").queue();
                    }
                    break;
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
    public static void sendHypixelStats(@Nonnull WallyUser commandSender, @Nonnull Message message, @Nonnull String data, @Nonnull String uuid, boolean edit) {
        if (edit) {
            switch (commandSender.getDisplayPreference()) {
                case TEXT:
                    message.editMessage(generateHypixelStatsMessage(data)).queue();
                    break;
                case EMBED:
                    message.editMessageEmbeds(EmbedUtils.generateHypixelStatsEmbed(data)).queue();
                    break;
                default:
                    message.editMessage("Soon...").queue();
                    break;
            }
        } else {
            final SelectMenu menu = createAccountMenu(commandSender, uuid, "stats");

            switch (commandSender.getDisplayPreference()) {
                case TEXT:
                    if (menu != null) {
                        message.reply(generateHypixelStatsMessage(data)).setActionRow(menu).queue();
                    } else {
                        message.reply(generateHypixelStatsMessage(data)).queue();
                    }
                    break;
                case EMBED:
                    if (menu != null) {
                        message.replyEmbeds(EmbedUtils.generateHypixelStatsEmbed(data)).setActionRow(menu).queue();
                    } else {
                        message.replyEmbeds(EmbedUtils.generateHypixelStatsEmbed(data)).queue();
                    }
                    break;
                default:
                    if (menu != null) {
                        message.reply("Soon...").setActionRow(menu).queue();
                    } else {
                        message.reply("Soon...").queue();
                    }
                    break;
            }
        }
    }

    /**
     * Create text with leaderboard information inside
     * @param leaderboard the leaderboard
     * @param page the leaderboard page
     * @return a message containing leaderboard page information
     */
    public static @Nonnull String generateLeaderboardMessage(@Nonnull Leaderboard leaderboard, int page) {
        final StringBuilder builder = new StringBuilder();
        builder.append("```yml").append(System.lineSeparator())
                .append(leaderboard.name).append(System.lineSeparator())
                .append(System.lineSeparator());

        int leaderboardPage = (page - 1) * 10;

        if (leaderboard.players.size() <= leaderboardPage) {
            leaderboardPage = leaderboard.players.size() - 10;
        }

        for (int i = leaderboardPage; i < leaderboardPage + 10; i++) {
            if (leaderboard.players.size() <= i) break;
            final StatsObject stats = leaderboard.players.get(i);
            builder.append("(#").append(i + 1).append(") ")
                    .append(stats.getName()).append(": ")
                    .append(
                            leaderboard.type.equals(Leaderboard.Type.OVERALL) ?
                                    Wally.getInstance().decimalFormat.format(stats.getOverall()) :
                                    Wally.getInstance().numberFormat.format(stats.getDataFromType(leaderboard.type)))
                    .append(System.lineSeparator());
        }
        builder.append("```");

        return builder.toString();
    }

    /**
     * Create a text with Practice stats information inside
     * @param data a valid Practice json data
     * @return a message containing every hole in the wall stats
     */
    public static @Nonnull String generatePracticeStatsMessage(@Nonnull String data) {
        final PracticeStats stats = new PracticeStats(data);

        return "```yml" + System.lineSeparator() +
                "Player name: " + stats.name + System.lineSeparator() +
                System.lineSeparator() +
                "Qualification: " + Wally.getInstance().numberFormat.format(stats.qualification) + " " + LeaderboardUtils.formatPosition(stats.qualificationPosition) + System.lineSeparator() +
                "Finals: " + Wally.getInstance().numberFormat.format(stats.finals) + " " + LeaderboardUtils.formatPosition(stats.finalsPosition) + System.lineSeparator() +
                "Lobby: " + Wally.getInstance().numberFormat.format(stats.lobby) + " " + LeaderboardUtils.formatPosition(stats.lobbyPosition) + System.lineSeparator() +
                "Wide Qualification: " + Wally.getInstance().numberFormat.format(stats.wideQualification) + " " + LeaderboardUtils.formatPosition(stats.wideQualificationPosition) + System.lineSeparator() +
                "Wide Finals: " + Wally.getInstance().numberFormat.format(stats.wideFinals) + " " + LeaderboardUtils.formatPosition(stats.wideFinalsPosition) + System.lineSeparator() +
                "Total Score: " + Wally.getInstance().numberFormat.format(stats.total) + " " + LeaderboardUtils.formatPosition(stats.totalPosition) + System.lineSeparator() +
                "Overall Rank: " + Wally.getInstance().decimalFormat.format(stats.overall) + " " + LeaderboardUtils.formatPosition(stats.overallPosition) + System.lineSeparator() +
                "```";
    }

    /**
     * Create a text with Hypixel stats information inside
     * @param data a valid Hypixel json data
     * @return a message containing every hole in the wall stats
     */
    public static @Nonnull String generateHypixelStatsMessage(@Nonnull String data) {
        final HypixelStats stats = new HypixelStats(data);

        return "```yml" + System.lineSeparator() +
                "Player name: " + stats.name + System.lineSeparator() +
                System.lineSeparator() +
                "Wins: " + Wally.getInstance().numberFormat.format(stats.wins) + " " + LeaderboardUtils.formatPosition(stats.winsPosition) + System.lineSeparator() +
                "Walls: " + Wally.getInstance().numberFormat.format(stats.walls) + " " + LeaderboardUtils.formatPosition(stats.wallsPosition) + System.lineSeparator() +
                "Qualification: " + Wally.getInstance().numberFormat.format(stats.qualification) + " " + LeaderboardUtils.formatPosition(stats.qualificationPosition) + System.lineSeparator() +
                "Finals: " + Wally.getInstance().numberFormat.format(stats.finals) + " " + LeaderboardUtils.formatPosition(stats.finalsPosition) + System.lineSeparator() +
                "Q/F: " + Wally.getInstance().numberFormat.format(stats.total) + " " + LeaderboardUtils.formatPosition(stats.totalPosition) + System.lineSeparator() +
                "Q/F API: " + Wally.getInstance().numberFormat.format(stats.totalAPI) + " " + LeaderboardUtils.formatPosition(stats.totalAPIPosition) + System.lineSeparator() +
                "Challenges: " + Wally.getInstance().numberFormat.format(stats.challenges) + " " + LeaderboardUtils.formatPosition(stats.challengesPosition) + System.lineSeparator() +
                "Overall Rank: " + Wally.getInstance().decimalFormat.format(stats.overall) + " " + LeaderboardUtils.formatPosition(stats.overallPosition) + System.lineSeparator() +
                "```";
    }

    /**
     * Create a select menu to switch between a leaderboards
     * @param commandSender the command sender
     * @param menuName the menu name
     * @return a select menu ready to be used on a message
     */
    public static SelectMenu createLeaderboardMenu(@Nonnull WallyUser commandSender, @Nonnull String menuName, int page) {
        final SelectMenu.Builder menu = SelectMenu.create(commandSender.getMember().getId()+":"+menuName)
                .setRequiredRange(1, 1)
                .setPlaceholder("Select a leaderboard")
                .addOption(LeaderboardManager.getInstance().winsLeaderboardHypixel.name, "0:" + page)
                .addOption(LeaderboardManager.getInstance().wallLeaderboardHypixel.name, "1:" + page)
                .addOption(LeaderboardManager.getInstance().qualificationLeaderboardHypixel.name, "2:" + page)
                .addOption(LeaderboardManager.getInstance().finalsLeaderboardHypixel.name, "3:" + page)
                .addOption(LeaderboardManager.getInstance().totalLeaderboardHypixel.name, "4:" + page)
                .addOption(LeaderboardManager.getInstance().totalApiLeaderboardHypixel.name, "5:" + page)
                .addOption(LeaderboardManager.getInstance().challengesLeaderboardHypixel.name, "6:" + page)
                .addOption(LeaderboardManager.getInstance().overallLeaderboardHypixel.name, "7:" + page)
                .addOption(LeaderboardManager.getInstance().qualificationLeaderboardPractice.name, "8:" + page)
                .addOption(LeaderboardManager.getInstance().finalsLeaderboardPractice.name, "9:" + page)
                .addOption(LeaderboardManager.getInstance().lobbyLeaderboardPractice.name, "10:" + page)
                .addOption(LeaderboardManager.getInstance().wideQualificationLeaderboardPractice.name, "11:" + page)
                .addOption(LeaderboardManager.getInstance().wideFinalsLeaderboardPractice.name, "12:" + page)
                .addOption(LeaderboardManager.getInstance().totalLeaderboardPractice.name, "13:" + page)
                .addOption(LeaderboardManager.getInstance().overallLeaderboardPractice.name, "14:" + page);

        return (menu.build());
    }

    /**
     * Create a select menu to switch between a user accounts
     * @param commandSender the command sender
     * @param uuid the player uuid
     * @param menuName the menu name
     * @return a select menu ready to be used on a message
     */
    public static SelectMenu createAccountMenu(@Nonnull WallyUser commandSender, @Nonnull String uuid, @Nonnull String menuName) {
        final boolean linked = DatabaseUtils.isLinkedUUID(uuid);
        if (!linked) return null;

        final WallyUser wallyUser = DatabaseUtils.getWallyUserFromUUID(uuid);
        final File folder = new File("database/linked users/" + wallyUser.getMember().getId() + "/accounts/");
        final String[] entries = folder.list();
        if (entries == null || entries.length == 1) return null;

        final SelectMenu.Builder menu = SelectMenu.create(commandSender.getMember().getId()+":"+menuName)
                .setRequiredRange(1, 1)
                .setPlaceholder("Select an account");

        for (String s : entries) {
            String player = Mojang.getPlayerName(s);
            if (player == null) continue;
            menu.addOption(player, s);
        }

        return (menu.build());
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
    public static void sendLogButtonReply(@Nonnull WallyUser commandSender, @Nonnull Message message, @Nonnull String text) {
        if (commandSender.getPermissionLevel().equals(PermissionLevel.ADMIN)) {
            message.reply(text).setActionRow(
                            Button.primary(commandSender.getMember().getId() + ":" + message.getId(), "Send me the log"),
                            Button.danger(commandSender.getMember().getId() + ":" + UUID.randomUUID(), "Nevermind"))
                    .queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
        } else {
            message.reply(text).queue();
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
    public static Message getMessageFromHistory(@Nonnull MessageChannel messageChannel, @Nonnull String messageID, int historyCount) {
        final List<Message> history = messageChannel.getHistory().retrievePast(historyCount).complete();
        final Optional<Message> message = history.stream().filter(msg -> msg.getId().equals(messageID)).findFirst();

        return message.orElse(null);
    }
}
