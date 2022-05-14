package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.CommandUtils;
import com.blackoutburst.wally.utils.DatabaseUtils;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.io.FileReader;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CommandSetDisplayPreference extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        final String discordID = commandSender.getMember().getId();
        if (!DatabaseUtils.isLinked(discordID)) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "sorry not linked")).queue();
            return;
        }

        final UserOption option = DatabaseUtils.getWallyUserData(discordID);
        final String id = commandSender.getMember().getId()+":";

        message.reply(ConfigManager.getInstance().loadValue("replies", "select display preference")).setActionRow(
                option.getDisplayPreference().equals(DisplayPreference.CANVAS) ? Button.primary(id+UUID.randomUUID(), "CANVAS") : Button.secondary(id+UUID.randomUUID(), "CANVAS"),
                option.getDisplayPreference().equals(DisplayPreference.EMBED) ? Button.primary(id+UUID.randomUUID(), "EMBED") : Button.secondary(id+UUID.randomUUID(), "EMBED"),
                option.getDisplayPreference().equals(DisplayPreference.TEXT) ? Button.primary(id+UUID.randomUUID(), "TEXT") : Button.secondary(id+UUID.randomUUID(), "TEXT")
        ).queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
    }
}
