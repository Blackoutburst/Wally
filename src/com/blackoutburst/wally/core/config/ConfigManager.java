package com.blackoutburst.wally.core.config;

import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.core.Wally;
import com.google.gson.*;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;

public class ConfigManager {

    public final File configurationFile;

    private static ConfigManager instance = null;

    /**
     * Get the configuration manager instance
     * @return the config manager
     */
    public static @Nonnull ConfigManager getInstance() {
        if (instance == null)
            instance = new ConfigManager();

        return (instance);
    }

    /**
     * Create a new configuration manager
     * Try to load the config.json file if missing
     * an error message will be displayed and the program will abort
     */
    private ConfigManager() {
        final File configFile = new File("config/config.json");

        if (!configFile.exists()) {
            Wally.getInstance().logger.log(LogLevel.ERROR, "The configuration file [config/config.json] is missing!");
            System.exit(84);
        }
        configurationFile = configFile;
    }

    /**
     * Load a value from the config.json
     *
     * @param key the key name
     * @param value the object name
     * @return the object value as String
     */
    public String loadValue(@Nonnull String key, @Nonnull String value) {
        try {
            final Gson gson = new Gson();
            final JsonObject object = gson.fromJson(new FileReader(configurationFile), JsonObject.class);

            return (object.getAsJsonObject(key).get(value).getAsString());
        } catch (Exception e) {
            Wally.getInstance().logger.log(LogLevel.WARN, e.getMessage());
            return (null);
        }
    }
}
