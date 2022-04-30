package com.blackoutburst.wally.core.request;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.commands.PermissionLevel;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Mojang {

    /**
     * Get a player UUID using his current username
     *
     * @param playerName the player name
     * @return the player uuid
     */
    public static String getPlayerUUID(@Nonnull String playerName) {
        try {
            final String result = ConnectionManager.readFromURL("https://api.mojang.com/users/profiles/minecraft/" + playerName);

            final Gson gson = new Gson();
            final JsonObject object = gson.fromJson(result, JsonObject.class);

            Wally.getInstance().logger.log(LogLevel.INFO, "New Mojang request sent for [" + playerName + "]");

            return (object.get("id").getAsString());
        } catch (Exception e) {
            for (StackTraceElement stack : e.getStackTrace())
                Wally.getInstance().logger.log(LogLevel.ERROR, stack.toString());
            return (null);
        }
    }
}
