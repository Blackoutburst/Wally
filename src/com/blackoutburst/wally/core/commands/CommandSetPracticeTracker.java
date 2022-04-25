package com.blackoutburst.wally.core.commands;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
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

public class CommandSetPracticeTracker extends CommandExecutable {

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final JsonObject object = gson.fromJson(new FileReader(ConfigManager.getInstance().configurationFile), JsonObject.class);
            final Writer writer = new FileWriter(ConfigManager.getInstance().configurationFile.getPath());

            object.getAsJsonObject("channels").addProperty("practice tracker channel", channel.getId());
            writer.write(gson.toJson(object).replace("\\u0027", "'")); // Encoding is broken
            writer.flush();
            writer.close();
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "practice tracker channel updated").replace("%channel%", "<#"+channel.getId()+">")).queue();
        } catch (Exception e) {
            e.printStackTrace();
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "channel update error")).queue();
        }
    }
}