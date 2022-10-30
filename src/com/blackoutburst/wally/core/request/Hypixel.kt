package com.blackoutburst.wally.core.request

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.main.Main

object Hypixel {

    fun getPlayerStats(uuid: String, silent :Boolean = false): String? {
        return try {
            if (!silent)
                Wally.logger.log(LogLevel.INFO, "New Hypixel request sent for [$uuid]")
            val res = ConnectionManager.readFromURL("https://api.hypixel.net/player?uuid=$uuid&key=${Main.HYPIXEL_API}")
            if (res?.get(0) != '{')
                throw Exception("Invalid json")
            res
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
            null
        }
    }
}