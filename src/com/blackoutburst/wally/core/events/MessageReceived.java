package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.CommandManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nonnull;

public class MessageReceived {

    /**
     * Read incoming messages and turn them into potential commands
     *
     * @param event The message event
     */
    public static void run(@Nonnull MessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith(CommandManager.getPrefix())) return;
        if (!Wally.getInstance().isReady) {
            event.getMessage().reply(ConfigManager.getInstance().loadValue("replies", "still loading")).queue();
            return;
        }

        final String messageString = event.getMessage().getContentRaw();
        final String commandName = CommandUtils.getCommandName(messageString);
        final String[] commandArgs = CommandUtils.getCommandArgs(messageString);
        final Guild guild = event.getGuild();
        final MessageChannel messageChannel = event.getChannel();
        final Message message = event.getMessage();
        final Member member = event.getMember();
        if (member == null) return;

        final WallyUser commandSender = WallyUser.getUser(member);
        CommandManager.getInstance().runCommand(commandSender, commandName, commandArgs, guild, messageChannel, message);
    }
}
