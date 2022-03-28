package com.blackoutburst.wally.core.request;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Mojang {

    /**
     * Get a player UUID using his current username
     *
     * @param playerName the player name
     * @return the player uuid
     */
    public static String getPlayerUUID(@Nonnull String playerName) {
        try {
            final URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            final URLConnection connection = url.openConnection();
            final InputStream stream = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            final StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) builder.append(line).append(System.getProperty("line.separator"));

            stream.close();
            reader.close();

            final Gson gson = new Gson();
            final JsonObject object = gson.fromJson(builder.toString(), JsonObject.class);

            Wally.getInstance().logger.log(LogLevel.INFO, "New Mojang request sent for [" + playerName + "]");

            return (object.get("id").getAsString());
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.ERROR, e.getMessage());
            return (null);
        }
    }
}
