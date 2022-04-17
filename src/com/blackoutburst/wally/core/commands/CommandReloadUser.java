package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.File;

public class CommandReloadUser extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        final File rootFolder = new File("database/linked users/");
        if (!rootFolder.exists()) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "no user db")).queue();
            return;
        }

        final String[] entries = rootFolder.list();
        if (entries == null) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "no user db")).queue();
            return;
        }

        Wally.getInstance().loadUser();
        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "user reloaded")
                .replace("%users%", String.valueOf(Wally.getInstance().users.size()))
                .replace("%total%", String.valueOf(entries.length))).queue();
    }
}
