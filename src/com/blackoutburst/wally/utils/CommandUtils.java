package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.commands.CommandManager;
import net.dv8tion.jda.api.entities.Guild;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class CommandUtils {

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
