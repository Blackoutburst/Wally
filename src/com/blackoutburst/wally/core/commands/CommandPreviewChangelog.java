package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class CommandPreviewChangelog extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (!new File("changelog.txt").exists()) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "no changelog")).queue();
            return;
        }

        try {
            final BufferedReader reader = new BufferedReader(new FileReader("changelog.txt"));
            final StringBuilder changelog = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                changelog.append(line).append("\n");
            }
            reader.close();

            channel.sendMessage(changelog).queue();
            message.delete().queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
