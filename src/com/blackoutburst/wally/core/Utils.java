package com.blackoutburst.wally.core;

import com.blackoutburst.wally.core.commands.CommandManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import javax.annotation.Nonnull;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

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
            e.printStackTrace();
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
            return (defaultValue);
        }
    }
}
