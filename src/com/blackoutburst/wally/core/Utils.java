package com.blackoutburst.wally.core;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.util.List;

public class Utils {

    public static Message getMessageFromHistory(@Nonnull MessageChannel messageChannel, @Nonnull String messageID, int historyCount) {
        final List<Message> history = messageChannel.getHistory().retrievePast(historyCount).complete();

        for (Message msg : history) {
            if (msg.getId().equals(messageID)) {
                return msg;
            }
        }
        return null;
    }
}
