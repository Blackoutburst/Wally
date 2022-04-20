package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class CommandSay extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length == 0) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"say [message]")).queue();
            return;
        }

        final String[] tmp = message.getContentRaw().trim().split(" ");
        final List<String> words = Arrays.asList(Arrays.copyOfRange(tmp, 1, tmp.length));
        final StringBuilder builder = new StringBuilder();

        words.forEach(w -> builder.append(w).append(' '));

        message.delete().queue();
        channel.sendMessage(builder.toString()).queue();
    }
}
