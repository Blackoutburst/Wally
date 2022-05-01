package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public abstract class CommandExecutable {

    protected abstract void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args,
                                         @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message);
}
