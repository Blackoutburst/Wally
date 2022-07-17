package com.blackoutburst.wally.core.events

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.logger.LogLevel
import net.dv8tion.jda.api.events.ReadyEvent
import kotlin.system.exitProcess

object Ready {

    fun run(event: ReadyEvent) {
        Wally.jda = event.jda
        Wally.loadUser()
        Wally.hitwDiscord = event.jda.getGuildById(ConfigManager.loadValue("servers", "dev server"))
        Wally.devServer = event.jda.getGuildById(ConfigManager.loadValue("servers", "dev server"))

        if (Wally.hitwDiscord == null) {
            Wally.logger.log(LogLevel.ERROR, "Couldn't retrieve the Hole in the Wall discord server!")
            exitProcess(0)
        }

        Wally.logger.log(LogLevel.INFO, "Discord servers retrieved successfully")

        /*
        HypixelTracker.run()
        PracticeTracker.run()
        Wally.logger.log(LogLevel.INFO, "Trackers started successfully")

        HypixelLeaderboardUpdater.run()
        PracticeLeaderboardUpdater.run()
        Wally.logger.log(LogLevel.INFO, "Leaderboard Updaters started successfully")
         */
        Wally.threadSet = Thread.getAllStackTraces().keys
    }
}