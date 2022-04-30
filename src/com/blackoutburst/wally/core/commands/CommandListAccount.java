package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.UserOption;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.request.Mojang;
import com.blackoutburst.wally.utils.Utils;
import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;

public class CommandListAccount extends CommandExecutable{

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length < 1) {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"listaccounts [discordID]")).queue();
            return;
        }
        final String discordID = Utils.getDiscordIDFromTwoArgs(guild, args);
        final File folder = new File("database/linked users/" + discordID + "/accounts/");
        final String[] entries = folder.list();
        final Gson gson = new Gson();

        UserOption data;
        try {
            final FileReader reader = new FileReader("database/linked users/" + discordID + "/data.json");
            data = gson.fromJson(reader, UserOption.class);
            reader.close();
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            Utils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "account list error"));
            return;
        }

        final String mainUUID = data.getMainAccountUUID();
        final StringBuilder builder = new StringBuilder();
        builder.append("```md").append(System.lineSeparator())
            .append("#").append(Mojang.getPlayerName(mainUUID)).append(System.lineSeparator());

        for (String s : entries) {
            if (s.equals(mainUUID)) continue;
            builder.append(Mojang.getPlayerName(s)).append(System.lineSeparator());
        }
        builder.append("```").append(System.lineSeparator());
        channel.sendMessage(builder).queue();
    }
}
