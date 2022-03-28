package com.blackoutburst.wally.core.request;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.logger.LogLevel;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Hypixel {

    /**
     * Get a player Hypixel stats using the Hypixel API
     *
     * @param uuid the player uuid
     * @return the player stats as string (JSON)
     */
    public static String getPlayerStats(@Nonnull String uuid) {
        try {
            final String apiKey = ConfigManager.getInstance().loadValue("settings", "hypixel api");
            final URL url = new URL("https://api.hypixel.net/player?uuid=" + uuid + "&key=" + apiKey);
            final URLConnection connection = url.openConnection();
            final InputStream stream = connection.getInputStream();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            final StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) builder.append(line).append(System.getProperty("line.separator"));

            stream.close();
            reader.close();

            Wally.getInstance().logger.log(LogLevel.INFO, "New Hypixel request sent for [" + uuid + "]");
            return (builder.toString());
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.ERROR, e.getMessage());
            return (null);
        }
    }
}
