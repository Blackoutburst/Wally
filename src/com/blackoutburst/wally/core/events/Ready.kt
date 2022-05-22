package com.blackoutburst.wally.core.events

import net.dv8tion.jda.api.events.ReadyEvent
import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import kotlin.system.exitProcess

object Ready {

    /**
     * This even fire when JDA finish loading
     * We use it to get the Hole in the Wall discord server
     * @param event the JDA event
     */
    fun run(event: ReadyEvent) {
        Wally.jda = event.jda
        Wally.loadUser()
        Wally.hitwDiscord = event.jda.getGuildById(ConfigManager.loadValue("servers", "dev server"))

        if (Wally.hitwDiscord == null) {
            Wally.logger.log(LogLevel.ERROR, "Couldn't retrieve the Hole in the Wall discord server!")
            exitProcess(0)
        }

        Wally.logger.log(LogLevel.INFO, "Hole in the Wall discord retrieved successfully")
    }
}