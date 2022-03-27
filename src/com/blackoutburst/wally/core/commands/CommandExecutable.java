package com.blackoutburst.wally.core.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public abstract class CommandExecutable {

    protected abstract boolean onCommand(@Nonnull Member commandSender, @Nonnull String commandName, @Nonnull String[] args,
                                      @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message);
}
