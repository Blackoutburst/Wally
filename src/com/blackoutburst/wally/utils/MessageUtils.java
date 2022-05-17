package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.core.statsobject.HypixelStats;
import com.blackoutburst.wally.core.statsobject.PracticeStats;
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
     * Create a text with Practice stats information inside
     * @param data a valid Practice json data
     * @return a message containing every hole in the wall stats
     */
    public static String generatePracticeStatsMessage(@Nonnull String data) {
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
    public static String generateHypixelStatsMessage(@Nonnull String data) {
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
     * Send the skin embed message without menu if the player isn't linked
     * If the player is linked and possess multiples account a drop menu
     * is added below to allow the user to switch between his skin
     *
     * @param commandSender the command sender
     * @param uuid the player uuid
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
