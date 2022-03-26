package com.blackoutburst.wally.core.events;

import com.blackoutburst.wally.core.ConfigManager;
import com.blackoutburst.wally.core.Logger;
import com.blackoutburst.wally.core.Wally;
import net.dv8tion.jda.api.events.ReadyEvent;

import javax.annotation.Nonnull;

public class Ready {

    public static void run(@Nonnull ReadyEvent event) {
        Wally.hitwDiscord = event.getJDA().getGuildById(ConfigManager.loadValue("servers", "dev server"));

        if (Wally.hitwDiscord == null) {
            Logger.error("Couldn't retrieve the Hole in the Wall discord server!");
            System.exit(84);
        } else {
            Logger.info("Hole in the Wall discord retrieved successfully");
        }
    }
}
