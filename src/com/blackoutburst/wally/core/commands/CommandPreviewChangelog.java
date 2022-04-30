package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CommandPreviewChangelog extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (!new File("config/changelog.txt").exists()) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "no changelog")).queue();
            return;
        }

        final EmbedBuilder embed = Utils.generateChangelog();
        if (embed == null) {
            Utils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "changelog error"));
            return;
        }

        channel.sendMessageEmbeds(embed.build()).queue();
        message.delete().queue();
    }
}
