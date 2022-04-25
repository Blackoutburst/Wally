package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;

public class CommandShowPracticeTracker extends CommandExecutable {

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "practice tracker location")
                .replace("%channel%", "<#"+ConfigManager.getInstance().loadValue("channels", "practice tracker channel")+">")).queue();
    }
}