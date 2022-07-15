package com.blackoutburst.wally.core.request

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel
import com.blackoutburst.wally.main.Main.Companion.HYPIXEL_API

object Hypixel {

    fun getPlayerStats(uuid: String, silent :Boolean = false): String? {
        //if (uuid == "640459a7a5c8417bbb06f6e6d28614a1") return null
        if (!silent)
            Wally.logger.log(LogLevel.INFO, "New Hypixel request sent for [$uuid]")
        return ConnectionManager.readFromURL("https://api.hypixel.net/player?uuid=$uuid&key=$HYPIXEL_API")
    }
}