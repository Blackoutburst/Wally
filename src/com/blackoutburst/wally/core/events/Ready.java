package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.events.ReadyEvent;

import javax.annotation.Nonnull;

public class Ready {

    public static void run(@Nonnull ReadyEvent event) {
        Wally.hitwDiscord = event.getJDA().getGuildById(ConfigManager.loadValue("servers", "dev server"));

        if (Wally.hitwDiscord == null) {
            Wally.LOG.log(LogLevel.ERROR, "Couldn't retrieve the Hole in the Wall discord server!");
            System.exit(84);
        } else {
            Wally.LOG.log(LogLevel.INFO, "Hole in the Wall discord retrieved successfully");
        }
    }
}
