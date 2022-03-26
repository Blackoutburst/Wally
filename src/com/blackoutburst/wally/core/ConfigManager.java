package com.blackoutburst.wally.core;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static boolean isInitialized = false;

    public static void init() {
        if (isInitialized) return;
        isInitialized = true;

        final File configFile = new File("config/config.json");

        if (!configFile.exists()) {
            Logger.error("The configuration file [config/config.json] is missing!");
            System.exit(84);
        }
    }
}
