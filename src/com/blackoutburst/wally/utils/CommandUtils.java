package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.CommandManager;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.request.Hypixel;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.core.request.PracticeServer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import javax.annotation.Nonnull;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class CommandUtils {

    /**
     * Create a text with Hypixel stats information inside
     * @param data a valid Hypixel json data
     * @return a message containing every hole in the wall stats
     */
    public static String generateHypixelStatsMessage(@Nonnull String data) {
        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        final DecimalFormat df = (DecimalFormat) nf;

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
                "Wins: " + df.format(wins) + System.lineSeparator() +
                "Walls: " + df.format(walls) + System.lineSeparator() +
                "Qualification: " + df.format(qualification) + System.lineSeparator() +
                "Finals: " + df.format(finals) + System.lineSeparator() +
                "Q/F: " + df.format(QF) + System.lineSeparator() +
                "Q/F API: " + df.format(QFAPI) + System.lineSeparator() +
                "Challenges: " + df.format(challenges) + System.lineSeparator() +
                "```";
    }

    /**
     * Create user or alt account data
     * manage error by steps
     *
     * @param isAltAccount decide if the account is an alt account or a main
     * @param guild the server running the command
     * @param args the command args
     * @param message the command message
     * @param discordID the user discordID
     * @param channel the message channel
     * @param commandSender the command sender
     */
    public static void createUserData(boolean isAltAccount, @Nonnull Guild guild, @Nonnull String[] args, @Nonnull Message message, @Nonnull String discordID, @Nonnull MessageChannel channel, @Nonnull WallyUser commandSender) {
        final String playerName = CommandUtils.getNonDiscordIDArgFromTwoArgs(guild, args);
        if (playerName == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "could not get name")).queue();
            return;
        }

        final String playerUUID = Mojang.getPlayerUUID(playerName);
        if (playerUUID == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not in mojang").replace("%name%", playerName)).queue();
            return;
        }

        final String hypixelData = Hypixel.getPlayerStats(playerUUID);
        if (hypixelData == null) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not in hypixel").replace("%name%", playerName)).queue();
            return;
        }

        String practiceData = PracticeServer.getPlayerStats(playerUUID);
        if (practiceData == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "could not load practice data").replace("%name%", playerName)).queue();
            practiceData = "";
        }

        final UserOption wallyProfile = new UserOption(PermissionLevel.MEMBER, playerUUID, guild.getId());

        if (isAltAccount) {
            if (!DatabaseManager.getInstance().createAltData(discordID, playerUUID, hypixelData, practiceData)) {
                MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "add alt error"));
                return;
            }
            FilesUtils.excludeFromLb(playerUUID);
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "add alt success").replace("%name%", playerName)
                    .replace("%user%", "<@"+discordID+">")).queue();
            Wally.getInstance().logger.log(LogLevel.INFO, playerName + " was added as alt to " + discordID);
        } else {
            if (!DatabaseManager.getInstance().createUserData(discordID, playerUUID, hypixelData, practiceData, wallyProfile)) {
                MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "link error"));
                return;
            }
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "link success").replace("%name%", playerName)).queue();
            Wally.getInstance().logger.log(LogLevel.INFO, playerName + " was linked to " + discordID);
        }
    }

    /**
     * Return the discord ID from the command args
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the discord id
     */
    public static String getDiscordIDFromTwoArgs(@Nonnull Guild guild, @Nonnull String[] args) {
        try {
            if (guild.getMemberById(args[0]) != null)
                return (args[0]);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        try {
            if (guild.getMemberById(args[1]) != null)
                return (args[1]);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        return (null);
    }

    /**
     * Get the args which isn't a discord id
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the second arg
     */
    public static String getNonDiscordIDArgFromTwoArgs(@Nonnull Guild guild, @Nonnull String[] args) {
        try {
            if (guild.getMemberById(args[0]) != null)
                return (args[1]);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        try {
            if (guild.getMemberById(args[1]) != null)
                return (args[0]);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        return (null);
    }

    /**
     * Get command arguments
     *
     * @param message the message as string
     * @return the command arguments
     */
    public static @Nonnull String[] getCommandArgs(@Nonnull String message) {
        final String[] tmp = message.trim().split(" ");

        return (Arrays.copyOfRange(tmp, 1, tmp.length));
    }

    /**
     * Get the command name
     *
     * @param message the message as string
     * @return the command name
     */
    public static @Nonnull String getCommandName(@Nonnull String message) {
        final String[] tmp = message.trim().split(" ");

        return (tmp[0].substring(CommandManager.getPrefix().length()).toLowerCase());
    }
}
