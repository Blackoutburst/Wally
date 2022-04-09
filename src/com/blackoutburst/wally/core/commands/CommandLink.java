package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandLink extends CommandExecutable{

    private String getDiscordID(@Nonnull String[] args) {
        return (null);
    }

    @Override
    protected boolean onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 2) return false;

        final String discordID = getDiscordID(args);
        if (discordID == null) {
            channel.sendMessage("").complete();
            return false;
        }

        return true;
    }
}
