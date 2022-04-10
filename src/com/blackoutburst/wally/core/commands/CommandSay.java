package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class CommandSay extends CommandExecutable{
    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        final String[] tmp = message.getContentRaw().trim().split(" ");
        final List<String> words = Arrays.asList(Arrays.copyOfRange(tmp, 1, tmp.length));
        final StringBuilder builder = new StringBuilder();

        words.forEach(w -> builder.append(w).append(' '));

        message.delete().queue();
        channel.sendMessage(builder.toString()).queue();

        return;
    }
}
