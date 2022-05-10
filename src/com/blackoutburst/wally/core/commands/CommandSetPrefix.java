package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.utils.MessageUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

public class CommandSetPrefix extends CommandExecutable {

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 1)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"setprefix [anything]")).queue();
            return;
        }

        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final JsonObject object = gson.fromJson(new FileReader(ConfigManager.getInstance().configurationFile), JsonObject.class);
            final Writer writer = new FileWriter(ConfigManager.getInstance().configurationFile.getPath());

            object.getAsJsonObject("settings").addProperty("command prefix", args[0]);
            writer.write(gson.toJson(object).replace("\\u0027", "'")); // Encoding is broken
            writer.flush();
            writer.close();
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "prefix update")
            .replace("%prefix%", args[0])).queue();
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            MessageUtils.sendLogButtonReply(commandSender, message, ConfigManager.getInstance().loadValue("replies", "prefix update error"));
        }
    }
}
