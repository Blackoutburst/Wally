package com.blackoutburst.wally.main

import com.blackoutburst.wally.core.config.ConfigManager
import com.blackoutburst.wally.core.database.DatabaseManager
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.exitProcess

class Main {

    companion object {
        val DISCORD_TOKEN = readEnv(0)
        val HYPIXEL_API = readEnv(1)
        val PRACTICE_SERVER_IP = readEnv(2)
        val PRACTICE_SERVER_PORT = readEnv(3)

        private fun readEnv(index: Int): String {
            return try {
                val lines = Files.readAllLines(Paths.get("config/.env"))
                lines[index].split("=").toTypedArray()[1].replace("\"", "")
            } catch (e: Exception) {
                println("Invalid .env file")
                exitProcess(0)
            }
        }
    }
}

fun main() {
    ConfigManager
    DatabaseManager
    LeaderboardManager
}
