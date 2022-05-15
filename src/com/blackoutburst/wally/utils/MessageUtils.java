package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.request.Mojang;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import javax.annotation.Nonnull;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
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

        final String name = APIUtils.getValuesFromAPI(data,"name", "Unknown");
        final int qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.qualification", "0"));
        final int finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.finals", "0"));
        final int lobby = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.lobby", "0"));
        final int wideQualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_qualification", "0"));
        final int wideFinals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_finals", "0"));
        final int total = qualification + finals + lobby + wideQualification + wideFinals;

        return "```yml" + System.lineSeparator() +
                "Player name: " + name + System.lineSeparator() +
                System.lineSeparator() +
                "Qualification: " + Wally.getInstance().decimalFormat.format(qualification) + System.lineSeparator() +
                "Finals: " + Wally.getInstance().decimalFormat.format(finals) + System.lineSeparator() +
                "Lobby: " + Wally.getInstance().decimalFormat.format(lobby) + System.lineSeparator() +
                "Wide Qualification: " + Wally.getInstance().decimalFormat.format(wideQualification) + System.lineSeparator() +
                "Wide Finals: " + Wally.getInstance().decimalFormat.format(wideFinals) + System.lineSeparator() +
                "Total Score: " + Wally.getInstance().decimalFormat.format(total) + System.lineSeparator() +
                "```";
    }

    /**
     * Create a text with Hypixel stats information inside
     * @param data a valid Hypixel json data
     * @return a message containing every hole in the wall stats
     */
    public static MessageEmbed generateHypixelStatsEmbed(@Nonnull String data) {
        final String name = APIUtils.getValuesFromAPI(data,"player.displayname", "Unknown");
        final int wins = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.wins_hole_in_the_wall", "0"));
        final int walls = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.rounds_hole_in_the_wall", "0"));
        final int qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.hitw_record_q", "0"));
        final int finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.hitw_record_f", "0"));
        final int QF = qualification + finals;
        final int QFAPI = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.achievements.arcade_hitw_practice_makes_perfect", "0"));
        final int challenges = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.challenges.all_time.ARCADE__hole_in_the_wall_challenge", "0"));

        return new EmbedBuilder().build();
    }

    /**
     * Create a text with Hypixel stats information inside
     * @param data a valid Hypixel json data
     * @return a message containing every hole in the wall stats
     */
    public static String generateHypixelStatsMessage(@Nonnull String data) {
        final String name = APIUtils.getValuesFromAPI(data,"player.displayname", "Unknown");
        final int wins = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.wins_hole_in_the_wall", "0"));
        final int walls = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.rounds_hole_in_the_wall", "0"));
        final int qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.hitw_record_q", "0"));
        final int finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.stats.Arcade.hitw_record_f", "0"));
        final int QF = qualification + finals;
        final int QFAPI = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.achievements.arcade_hitw_practice_makes_perfect", "0"));
        final int challenges = Integer.parseInt(APIUtils.getValuesFromAPI(data,"player.challenges.all_time.ARCADE__hole_in_the_wall_challenge", "0"));

        return "```yml" + System.lineSeparator() +
                "Player name: " + name + System.lineSeparator() +
                System.lineSeparator() +
                "Wins: " + Wally.getInstance().decimalFormat.format(wins) + System.lineSeparator() +
                "Walls: " + Wally.getInstance().decimalFormat.format(walls) + System.lineSeparator() +
                "Qualification: " + Wally.getInstance().decimalFormat.format(qualification) + System.lineSeparator() +
                "Finals: " + Wally.getInstance().decimalFormat.format(finals) + System.lineSeparator() +
                "Q/F: " + Wally.getInstance().decimalFormat.format(QF) + System.lineSeparator() +
                "Q/F API: " + Wally.getInstance().decimalFormat.format(QFAPI) + System.lineSeparator() +
                "Challenges: " + Wally.getInstance().decimalFormat.format(challenges) + System.lineSeparator() +
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
