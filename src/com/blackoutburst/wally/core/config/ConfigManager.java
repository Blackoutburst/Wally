package com.blackoutburst.wally.core.config;

import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.Wally;
import com.google.gson.*;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;

public class ConfigManager {

    private static boolean isInitialized = false;
    private static File configurationFile = null;

    public static void init() {
        if (isInitialized) return;
        isInitialized = true;

        final File configFile = new File("config/config.json");

        if (!configFile.exists()) {
            Wally.LOG.log(LogLevel.ERROR, "The configuration file [config/config.json] is missing!");
            System.exit(84);
        }
        configurationFile = configFile;
    }

    public static @Nonnull String loadValue(@Nonnull String key, @Nonnull String value) {
        try {
            final Gson gson = new Gson();
            final JsonObject object = gson.fromJson(new FileReader(configurationFile), JsonObject.class);

            return (object.getAsJsonObject(key).get(value).getAsString());
        } catch (Exception e) {
            Wally.LOG.log(LogLevel.WARN, e.getMessage());
            return "";
        }
    }
}
