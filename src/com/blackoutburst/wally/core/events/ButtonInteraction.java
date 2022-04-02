package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.CommandManager;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ButtonInteraction {

    private static void rerunCommand(@Nonnull ButtonInteractionEvent event, MessageChannel messageChannel, Message message) {
        String label = event.getButton().getLabel();
        if (label.startsWith("Yes run")) {
            label = label.split(" ")[2];
        }

        final String messageString = event.getMessage().getContentRaw();
        final String commandName = label;
        final String[] commandArgs = getCommandArgs(messageString);
        final Guild guild = event.getGuild();
        final Member member = event.getMember();

        if (message == null) return;
        if (guild == null) return;
        if (member == null) return;

        WallyUser commandSender = WallyUser.getUser(member);
        if (commandSender == null) {
            commandSender = new WallyUser(member, PermissionLevel.MEMBER);
        }

        event.getMessage().delete().queue();
        CommandManager.getInstance().runCommand(commandSender, commandName, commandArgs, guild, messageChannel, message);
    }

    /**
     * Check when someone interacts with a button
     *
     * @param event The reaction event
     */
    protected static void run(@Nonnull ButtonInteractionEvent event) {
        if (event.getMember() == null) return;
        if (event.getButton().getId() == null) return;

        final MessageChannel messageChannel = event.getChannel();
        final String messageID = event.getButton().getId().split(":")[1];
        final List<Message> history = messageChannel.getHistory().retrievePast(5).complete();

        Message message = null;

        for (Message msg : history) {
            if (msg.getId().equals(messageID)) {
                message = msg;
            }
        }

        if (!event.getMember().getId().equals(event.getButton().getId().split(":")[0])) {
            if (message == null) {
                event.reply(ConfigManager.getInstance().loadValue("replies", "unauthorized button")).queue();
            } else {
                message.reply(ConfigManager.getInstance().loadValue("replies", "unauthorized button")).queue();
            }
            return;
        }

        if (event.getButton().getLabel().equals("No")) {
            event.getMessage().delete().queue();
            return;
        }

        rerunCommand(event, messageChannel, message);
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
