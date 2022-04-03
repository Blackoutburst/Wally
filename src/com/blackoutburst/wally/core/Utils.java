package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.commands.CommandManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class Utils {

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

        for (Message msg : history) {
            if (msg.getId().equals(messageID)) {
                return msg;
            }
        }
        return null;
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
