package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CommandSetLogLevel extends CommandExecutable{
  
  @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length == 0) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"setloglevel [INFO/WARN/ERROR/NONE]")).queue();
            return;
        }

        LogLevel level;

        try {
            level = LogLevel.valueOf(args[0].toUpperCase());
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());

            if (commandSender.getPermissionLevel().equals(PermissionLevel.ADMIN)) {
                message.reply(ConfigManager.getInstance().loadValue("replies", "not a log level").replace("%level%", args[0])).setActionRow(
                                Button.primary(commandSender.getMember().getId() + ":" + message.getId(), "Send me the log"),
                                Button.danger(commandSender.getMember().getId() + ":" + UUID.randomUUID(), "Nevermind"))
                        .queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
            } else {
                message.reply(ConfigManager.getInstance().loadValue("replies", "not a log level").replace("%level%", args[0])).queue();
            }

            return;
        }

        channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "change log level").replace("%level%", level.toString())).queue();
        Wally.getInstance().logger.setInternalLogLevel(level);
    }
}
