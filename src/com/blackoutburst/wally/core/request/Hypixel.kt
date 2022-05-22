package com.blackoutburst.wally.core.request

import com.blackoutburst.wally.main.Main.Companion.HYPIXEL_API
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import java.lang.Exception

object Hypixel {
    /**
     * Get a player Hypixel stats using the Hypixel API
     *
     * @param uuid the player uuid
     * @return the player stats as string (JSON)
     */
    fun getPlayerStats(uuid: String): String? {
        return try {
            val result =
                ConnectionManager.readFromURL("https://api.hypixel.net/player?uuid=$uuid&key=$HYPIXEL_API")
            Wally.logger.log(LogLevel.INFO, "New Hypixel request sent for [$uuid]")
            result
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e.stackTrace)
            null
        }
    }
}