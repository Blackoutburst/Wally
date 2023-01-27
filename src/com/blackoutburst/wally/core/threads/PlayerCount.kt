package com.blackoutburst.wally.core.threads

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.request.Hypixel
import java.lang.Thread.sleep

object PlayerCount {

    fun run() {
        val thread = Thread {
            while (true) {
                try {
                    Wally.hitwDiscord!!.getVoiceChannelById(ConfigManager.loadValue("channels", "player count"))!!.manager.setName("${Hypixel.getPlayerCount()} playing HitW").queue()
                    sleep(60000)
                } catch (e: Exception) {
                    Wally.logger.logStackTrace(e)
                }
            }
        }
        thread.start()
        thread.name = "Player Count"
    }
}