package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Objects;

public class CommandSetConfig extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (message.getAttachments().size() == 0) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "missing config attachment")).queue();
        }

        final Message.Attachment file = message.getAttachments().get(0);

        if (Objects.equals(file.getFileExtension(), "json")) {
            file.downloadToFile(new File("config/config.json"));
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "config updated")).queue();
        } else {
            message.reply(ConfigManager.getInstance().loadValue("replies", "not a json")).queue();
        }
    }
}
