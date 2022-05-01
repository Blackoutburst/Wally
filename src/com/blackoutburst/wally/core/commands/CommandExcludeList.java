package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CommandExcludeList extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (!new File("config/excluded.txt").exists()) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "no exclude file")).queue();
            return;
        }

        try {
            final BufferedReader reader = new BufferedReader(new FileReader("config/excluded.txt"));
            final StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();

            channel.sendMessage(builder).queue();
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            Utils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "exclude error"));
        }
    }
}
