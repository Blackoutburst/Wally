package com.blackoutburst.wally.core.request

import com.blackoutburst.wally.main.Main.Companion.HYPIXEL_API
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.logger.LogLevel

object Hypixel {

    fun getPlayerStats(uuid: String): String? {
        Wally.logger.log(LogLevel.INFO, "New Hypixel request sent for [$uuid]")
        return ConnectionManager.readFromURL("https://api.hypixel.net/player?uuid=$uuid&key=$HYPIXEL_API")
    }
}