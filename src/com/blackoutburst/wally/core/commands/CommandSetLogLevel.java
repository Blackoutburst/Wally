package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class CommandSetLogLevel extends CommandExecutable{
  
  @Override
    protected void onCommand(@NotNull WallyUser commandSender, @NotNull String commandName, @NotNull String[] args, @NotNull Guild guild, @NotNull MessageChannel channel, @NotNull Message message) {
        if (args.length == 0) return;

        LogLevel level;

        try {
            level = LogLevel.valueOf(args[0]);
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "not a log level").replace("%level%", args[0])).complete();
            return;
        }

        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "change log level").replace("%level%", level.toString())).complete();
        Wally.getInstance().logger.setInternalLogLevel(level);
    }
}
