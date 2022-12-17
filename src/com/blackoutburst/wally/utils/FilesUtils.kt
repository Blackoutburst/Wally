package com.blackoutburst.wally.utils

import com.blackoutburst.wally.core.Wally
import com.blackoutburst.wally.core.leaderboard.LeaderboardManager.removePlayer
import java.io.File
import java.nio.file.Files
import java.util.stream.Collectors
import java.nio.file.StandardOpenOption
import java.nio.file.Paths

object FilesUtils {

    fun isExcluded(uuid: String?): Boolean {
        try {
            val file = File("config/excluded.txt")
            val lines = Files.lines(file.toPath())


            val data = lines.anyMatch { line: String ->
                line.contains(uuid!!)
            }

            lines.close()

            return data
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
        }
        return false
    }

    fun removeExclusion(uuid: String?) {
        try {
            val file = File("config/excluded.txt")
            val lines = Files.lines(file.toPath())
            val out = lines
                .filter { line: String -> !line.contains(uuid!!) }
                .collect(Collectors.toList())

            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)
            lines.close()
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
        }
    }

    fun excludeFromLb(uuid: String) {
        try {
            removePlayer(uuid)
            if (!isExcluded(uuid)) {
                Files.write(Paths.get("config/excluded.txt"), "$uuid\n".toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.APPEND)
            }
        } catch (e: Exception) {
            Wally.logger.logStackTrace(e)
        }
    }
}
