package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.database.DatabaseManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.File;

public class CommandLeaderboard extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 1) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"leaderboard [page]")).queue();
            return;
        }
    }
}
