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
      final String id = commandSender.getMember().getId()+":";

      message.reply(ConfigManager.getInstance().loadValue("replies", "select log level")).setActionRow(
              Wally.getInstance().logger.getInternalLogLevel().equals(LogLevel.INFO) ? Button.primary(id+UUID.randomUUID(), "INFO") : Button.secondary(id+UUID.randomUUID(), "INFO"),
              Wally.getInstance().logger.getInternalLogLevel().equals(LogLevel.WARN) ? Button.primary(id+UUID.randomUUID(), "WARN") : Button.secondary(id+UUID.randomUUID(), "WARN"),
              Wally.getInstance().logger.getInternalLogLevel().equals(LogLevel.ERROR) ? Button.primary(id+UUID.randomUUID(), "ERROR") : Button.secondary(id+UUID.randomUUID(), "ERROR"),
              Wally.getInstance().logger.getInternalLogLevel().equals(LogLevel.NONE) ? Button.primary(id+UUID.randomUUID(), "NONE") : Button.secondary(id+UUID.randomUUID(), "NONE")
      ).queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
    }
}
