package com.blackoutburst.wally.core.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandPing extends CommandExecutable{

    @Override
    protected boolean onCommand(@Nonnull Member commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        final long time = System.currentTimeMillis();

        channel.sendMessage("Pong !").queue(ping ->
                ping.editMessage(System.currentTimeMillis() - time+"ms").queue());

        return false;
    }
}
