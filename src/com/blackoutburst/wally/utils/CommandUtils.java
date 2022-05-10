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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class CommandUtils {

    public static void createUserData(@Nonnull boolean isAltAccount, @Nonnull Guild guild, @Nonnull String[] args, @Nonnull Message message, @Nonnull String discordID, @Nonnull MessageChannel channel, @Nonnull WallyUser commandSender) {
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
