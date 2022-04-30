package com.blackoutburst.wally.utils;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.WallyUser;
import com.blackoutburst.wally.core.commands.CommandManager;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Utils {

    /**
     * Generate the changelog embed message
     * @return the changelog embed
     */
    public static EmbedBuilder generateChangelog() {
        try {
            final String wallyURL = "https://github.com/Blackoutburst/Wally";
            final String wallyPfp = "https://cdn.discordapp.com/avatars/806086240427966484/72a4fc1ef53f7b729b9b54184e631b88.png?size=128";
            final BufferedReader reader = new BufferedReader(new FileReader("config/changelog.txt"));
            final StringBuilder changelog = new StringBuilder();
            final String title = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                changelog.append(line).append("\n");
            }
            reader.close();

            final EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor(String.valueOf(LocalDate.now()), wallyURL, wallyPfp)
                    .setTitle(title, wallyURL)
                    .addField("Changes", changelog.toString(), false)
                    .addField("", "[GitHub](https://github.com/Blackoutburst/Wally)", true)
                    .addField("", "[Patreon](https://www.patreon.com/Blackoutburst)", true)
                    .setColor(new Color(71, 165, 255))
                    .setFooter("Made with love by Blackoutburst", "https://www.blackoutburst.com/img/pfp.jpg");
            return (embed);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        return (null);
    }

    /**
     * Send an error reply,
     * if the user who generated the error message
     * does have the ADMIN permission level, extra button will be generated
     * to send the log file or cancel the action
     * @param commandSender the user who generated the error
     * @param message the discord message sent by the user
     * @param text the text used in the reply
     */
    public static void sendLogButtonReply(@Nonnull WallyUser commandSender, @Nonnull Message message, @Nonnull String text) {
        if (commandSender.getPermissionLevel().equals(PermissionLevel.ADMIN)) {
            message.reply(text).setActionRow(
                Button.primary(commandSender.getMember().getId() + ":" + message.getId(), "Send me the log"),
                Button.danger(commandSender.getMember().getId() + ":" + UUID.randomUUID(), "Nevermind"))
            .queue(r -> r.delete().queueAfter(Long.parseLong(ConfigManager.getInstance().loadValue("settings", "answer lifetime")), TimeUnit.SECONDS));
        } else {
            message.reply(text).queue();
        }
    }

    /**
     * Return the discord ID from the command args
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the discord id
     */
    public static String getDiscordIDFromTwoArgs(@Nonnull Guild guild, @Nonnull String[] args) {
        try {
            if (guild.getMemberById(args[0]) != null)
                return (args[0]);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        try {
            if (guild.getMemberById(args[1]) != null)
                return (args[1]);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        return (null);
    }

    /**
     * Get the args which isn't a discord id
     *
     * @param guild the guild used to run the command
     * @param args the command args
     * @return the second arg
     */
    public static String getNonDiscordIDArgFromTwoArgs(@Nonnull Guild guild, @Nonnull String[] args) {
        try {
            if (guild.getMemberById(args[0]) != null)
                return (args[1]);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        try {
            if (guild.getMemberById(args[1]) != null)
                return (args[0]);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
        }
        return (null);
    }

    /**
     * Retrieve a message from a message channel history
     *
     * @param messageChannel the message channel
     * @param messageID the message id
     * @param historyCount the number of message we need to go through
     * @return the message if found null otherwise
     */
    public static Message getMessageFromHistory(@Nonnull MessageChannel messageChannel, @Nonnull String messageID, int historyCount) {
        final List<Message> history = messageChannel.getHistory().retrievePast(historyCount).complete();

        for (Message msg : history) {
            if (msg.getId().equals(messageID)) {
                return msg;
            }
        }
        return null;
    }

    /**
     * Get command arguments
     *
     * @param message the message as string
     * @return the command arguments
     */
    public static @Nonnull String[] getCommandArgs(@Nonnull String message) {
        final String[] tmp = message.trim().split(" ");

        return (Arrays.copyOfRange(tmp, 1, tmp.length));
    }

    /**
     * Get the command name
     *
     * @param message the message as string
     * @return the command name
     */
    public static @Nonnull String getCommandName(@Nonnull String message) {
        final String[] tmp = message.trim().split(" ");

        return (tmp[0].substring(CommandManager.getPrefix().length()).toLowerCase());
    }

    /**
     * Return the API value of the specified data<br>
     *
     * Usage <br><br>
     *
     * <code>
     * String value = getValuesFromAPI(apiData, "player.stats.Arcade.hitw_record_q")
     * </code>
     *
     * @param apiData the apiData as JSON string
     * @param path the value path in the JSON
     * @param defaultValue is a value used if the object returned is null
     * @return the value if found
     */
    public static @Nonnull String getValuesFromAPI(@Nonnull String apiData, @Nonnull String path, @Nonnull String defaultValue) {
        try {
            final Gson gson = new Gson();
            final String[] splitPath = path.split("\\.");
            final JsonObject rootObject = gson.fromJson(apiData, JsonObject.class);
            final List<JsonObject> objects = new ArrayList<>();
            objects.add(rootObject);

            for (int i = 0; i < splitPath.length - 1; i++)
                objects.add(objects.get(i).getAsJsonObject(splitPath[i]));

            final String result = objects.get(objects.size() - 1).get(splitPath[splitPath.length - 1]).getAsString();

            return (result == null ? defaultValue : result);
        } catch (Exception e) {
            Wally.getInstance().logger.logStackTrace(e.getStackTrace());
            return (defaultValue);
        }
    }
}
