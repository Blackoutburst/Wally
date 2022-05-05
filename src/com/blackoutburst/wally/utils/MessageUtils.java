package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MessageUtils {

    /**
     * Send an error reply,
     * if the user who generated the error message
     * does have the ADMIN permission level, extra button will be generated
     * to send the log file or cancel the action
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

        for (Message msg : history) {
            if (msg.getId().equals(messageID)) {
                return msg;
            }
        }
        return null;
    }
}
