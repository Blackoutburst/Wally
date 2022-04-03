package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.CommandManager;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class MessageReceived {

    /**
     * Get the command name
     *
     * @param message the message as string
     * @return the command name
     */
    private static @Nonnull String getCommandName(@Nonnull String message) {
        final String[] tmp = message.trim().split(" ");

        return (tmp[0].substring(CommandManager.getPrefix().length()).toLowerCase());
    }

    /**
     * Get command arguments
     *
     * @param message the message as string
     * @return the command arguments
     */
    private static @Nonnull String[] getCommandArgs(@Nonnull String message) {
        final String[] tmp = message.trim().split(" ");

        return (Arrays.copyOfRange(tmp, 1, tmp.length));
    }

    /**
     * Read incoming messages and turn them into potential commands
     *
     * @param event The message event
     */
    public static void run(@Nonnull MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith(CommandManager.getPrefix())) return;

        final String messageString = event.getMessage().getContentRaw();
        final String commandName = getCommandName(messageString);
        final String[] commandArgs = getCommandArgs(messageString);
        final Guild guild = event.getGuild();
        final MessageChannel messageChannel = event.getChannel();
        final Message message = event.getMessage();
        final Member member = event.getMember();
        if (member == null) return;

        WallyUser commandSender = WallyUser.getUser(member);
        CommandManager.getInstance().runCommand(commandSender, commandName, commandArgs, guild, messageChannel, message);
    }
}
