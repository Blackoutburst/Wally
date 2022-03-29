package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class CommandSay extends CommandExecutable{
    @Override
    protected boolean onCommand(@NotNull WallyUser commandSender, @NotNull String commandName, @NotNull String[] args, @NotNull Guild guild, @NotNull MessageChannel channel, @NotNull Message message) {
        final String text = message.getContentRaw().replace(CommandManager.getPrefix()+commandName+" ", "");

        message.delete().queue();
        channel.sendMessage(text).queue();

        return true;
    }
}
