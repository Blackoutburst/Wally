package com.blackoutburst.wally.core.buttons;

import com.blackoutburst.wally.utils.Utils;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.CommandManager;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import javax.annotation.Nonnull;

public class ButtonCommand extends ButtonExecutable {

    @Override
    protected void onClick(@Nonnull ButtonInteractionEvent event) {
        final MessageChannel messageChannel = event.getMessageChannel();
        final String messageID = event.getButton().getId().split(":")[1];
        final Message message = Utils.getMessageFromHistory(messageChannel, messageID, 5);

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
        final String[] commandArgs = Utils.getCommandArgs(messageString);
        final Guild guild = event.getGuild();
        final Member member = event.getMember();
        if (guild == null) return;
        if (member == null) return;

        WallyUser commandSender = WallyUser.getUser(member);
        CommandManager.getInstance().runCommand(commandSender, commandName, commandArgs, guild, messageChannel, message);

        event.getMessage().delete().queue();
    }
}
