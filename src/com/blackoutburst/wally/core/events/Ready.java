package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.config.ConfigManager;
import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import net.dv8tion.jda.api.events.ReadyEvent;

import javax.annotation.Nonnull;

public class Ready {

    /**
     * This even fire when JDA finish loading
     * We use it to get the Hole in the Wall discord server
     * @param event the JDA event
     */
    protected static void run(@Nonnull ReadyEvent event) {
        Wally.getInstance().jda = event.getJDA();
        Wally.getInstance().loadUser();
        Wally.hitwDiscord = event.getJDA().getGuildById(ConfigManager.getInstance().loadValue("servers", "dev server"));

        if (Wally.hitwDiscord == null) {
            Wally.getInstance().logger.log(LogLevel.ERROR, "Couldn't retrieve the Hole in the Wall discord server!");
            System.exit(0);
        } else {
            Wally.getInstance().logger.log(LogLevel.INFO, "Hole in the Wall discord retrieved successfully");
        }
    }
}
