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
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CommandSetAnswerLifetime extends CommandExecutable {

    @Override
    protected void onCommand(@Nonnull WallyUser commandSender, @Nonnull String commandName, @Nonnull String[] args, @Nonnull Guild guild, @Nonnull MessageChannel channel, @Nonnull Message message) {
        if (args.length != 1)  {
            message.reply(ConfigManager.getInstance().loadValue("replies", "invalid command usage")
                    .replace("%usage%", CommandManager.getPrefix()+"setanswerlifetime [seconds]")).queue();
            return;
        }

        long value;

        try {
            value = Long.parseLong(args[0]);
        } catch (Exception e) {
            for (StackTraceElement stack : e.getStackTrace())
                Wally.getInstance().logger.log(LogLevel.ERROR, stack.toString());

            if (commandSender.getPermissionLevel().equals(PermissionLevel.ADMIN)) {
                message.reply(ConfigManager.getInstance().loadValue("replies", "not a number")
                                .replace("%num%", args[0])).setActionRow(
                                Button.primary(commandSender.getMember().getId() + ":" + message.getId(), "Send me the log"),
                                Button.danger(commandSender.getMember().getId() + ":" + UUID.randomUUID(), "Nevermind"))
                        .queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
            } else {
                message.reply(ConfigManager.getInstance().loadValue("replies", "not a number")
                        .replace("%num%", args[0])).queue();
            }
            return;
        }

        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final JsonObject object = gson.fromJson(new FileReader(ConfigManager.getInstance().configurationFile), JsonObject.class);
            final Writer writer = new FileWriter(ConfigManager.getInstance().configurationFile.getPath());

            object.getAsJsonObject("settings").addProperty("answer lifetime", value);
            writer.write(gson.toJson(object).replace("\\u0027", "'")); // Encoding is broken
            writer.flush();
            writer.close();
            channel.sendMessage(ConfigManager.getInstance().loadValue("replies", "answer life update")
            .replace("%seconds%", args[0])).queue();
        } catch (Exception e) {
            for (StackTraceElement stack : e.getStackTrace())
                Wally.getInstance().logger.log(LogLevel.ERROR, stack.toString());

            if (commandSender.getPermissionLevel().equals(PermissionLevel.ADMIN)) {
                message.reply(ConfigManager.getInstance().loadValue("replies", "answer life error")).setActionRow(
                                Button.primary(commandSender.getMember().getId() + ":" + message.getId(), "Send me the log"),
                                Button.danger(commandSender.getMember().getId() + ":" + UUID.randomUUID(), "Nevermind"))
                        .queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
            } else {
                message.reply(ConfigManager.getInstance().loadValue("replies", "answer life error")).queue();
            }
        }
    }
}
