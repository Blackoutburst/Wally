package com.blackoutburst.wally.core.request;

import com.blackoutburst.wally.core.Wally;
import com.blackoutburst.wally.core.logger.LogLevel;
import com.blackoutburst.wally.main.Main;

import javax.annotation.Nonnull;

public class Hypixel {

    /**
     * Get a player Hypixel stats using the Hypixel API
     *
     * @param uuid the player uuid
     * @return the player stats as string (JSON)
     */
    public static String getPlayerStats(@Nonnull String uuid) {
        try {
            final String result = ConnectionManager.readFromURL("https://api.hypixel.net/player?uuid=" + uuid + "&key=" + Main.HYPIXEL_API);

            Wally.getInstance().logger.log(LogLevel.INFO, "New Hypixel request sent for [" + uuid + "]");
            return (result);
        } catch (Exception e) {
            for (StackTraceElement stack : e.getStackTrace())
                Wally.getInstance().logger.log(LogLevel.ERROR, stack.toString());
            return (null);
        }
    }
}
