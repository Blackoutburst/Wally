package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.File;

public class CommandUnlink extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 1) return;

        final File folder = new File("database/linked users/" + args[0]);

        if (!folder.exists()) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "unlink not link")).complete();
            return;
        }

        if (!DatabaseManager.getInstance().cleanFolder(folder)) {
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "unlink error")).complete();
            return;
        }

        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "unlink success")).complete();
        Wally.getInstance().logger.log(LogLevel.INFO, args[0] + " got unlinked");

        return;
    }
}
