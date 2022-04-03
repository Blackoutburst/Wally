package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.Utils;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.CommandManager;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class ButtonInteraction {


    private static void rerunCommand(@Nonnull ButtonInteractionEvent event, MessageChannel messageChannel) {
        final String messageID = event.getButton().getId().split(":")[1];
        final Message message = Utils.getMessageFromHistory(messageChannel, messageID);
        if (message == null) {
            messageChannel.sendMessage("<@"+event.getMember().getId()+">"+
                    ConfigManager.getInstance().loadValue("replies", "message deleted")).queue();
            return;
        }

        String label = event.getButton().getLabel();
        if (label.startsWith("Yes run")) {
            label = label.split(" ")[2];
        }

        final String messageString = message.getContentRaw();
        final String commandName = label;
        final String[] commandArgs = getCommandArgs(messageString);
        final Guild guild = event.getGuild();
        final Member member = event.getMember();

        WallyUser commandSender = WallyUser.getUser(member);
        CommandManager.getInstance().runCommand(commandSender, commandName, commandArgs, guild, messageChannel, message);
    }

    /**
     * Check when someone interacts with a button
     *
     * @param event The reaction event
     */
    protected static void run(@Nonnull ButtonInteractionEvent event) {
        if (event.getGuild() == null) return;
        if (event.getMember() == null) return;
        if (event.getButton().getId() == null) return;

        final String label = event.getButton().getLabel();
        final String ownerID = event.getButton().getId().split(":")[0];
        final MessageChannel messageChannel = event.getChannel();

        if (!event.getMember().getId().equals(ownerID)) {
            messageChannel.sendMessage("<@"+event.getMember().getId()+">"+
                    ConfigManager.getInstance().loadValue("replies", "unauthorized button")).queue();
            return;
        }

        if (label.equals("No")) {
            event.getMessage().delete().queue();
            return;
        }

        if (label.startsWith("Yes run")) {
            rerunCommand(event, messageChannel);
        }
        event.getMessage().delete().queue();
    }

    /**
     * Get command arguments
     *
     * @param message the message as string
     * @return the command arguments
     */
    private static @Nonnull String[] getCommandArgs(@Nonnull String message) {
        final String[] tmp = message.trim().split(" ");

        return (Arrays.copyOfRange(tmp, 2, tmp.length));
    }
}
