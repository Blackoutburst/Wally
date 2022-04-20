package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CommandPoll extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length == 0) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"poll [message]")).queue();
            return;
        }

        final String[] tmp = message.getContentRaw().trim().split(" ");
        final List<String> words = Arrays.asList(Arrays.copyOfRange(tmp, 1, tmp.length));
        final StringBuilder builder = new StringBuilder();

        words.forEach(w -> builder.append(w).append(' '));

        final String iconURL = commandSender.getMember().getUser().getAvatarUrl();
        final String wallyURL = "https://github.com/Blackoutburst/Wally";

        final EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(commandSender.getMember().getEffectiveName(), wallyURL, iconURL)
                .setTitle(builder.toString())
                .setColor(new Color(71, 165, 255))
                .setFooter("Created on "+LocalDate.now());

        message.delete().queue();
        channel.sendMessageEmbeds(embed.build()).queue(msg -> {
            msg.addReaction("U+1F44D").queue();
            msg.addReaction("U+1F44E").queue();
        });
    }
}
