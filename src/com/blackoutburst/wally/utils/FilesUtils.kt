package com.blackoutburst.wally.utils

import java.io.IOException
import com.blackoutburst.wally.core.Wally
import java.io.File
import java.nio.file.Files
import java.util.stream.Collectors
import java.nio.file.StandardOpenOption
import java.nio.file.Paths

object FilesUtils {

    fun isExcluded(uuid: String?): Boolean {
        try {
            val file = File("config/excluded.txt")

            return Files.lines(file.toPath()).anyMatch { line: String ->
                line.contains(uuid!!)
            }
        } catch (e: IOException) {
            Wally.logger.logStackTrace(e.stackTrace)
        }
        return false
    }

    fun removeExclusion(uuid: String?) {
        try {
            val file = File("config/excluded.txt")
            val out = Files.lines(file.toPath())
                .filter { line: String -> !line.contains(uuid!!) }
                .collect(Collectors.toList())
            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)
        } catch (e: IOException) {
            Wally.logger.logStackTrace(e.stackTrace)
        }
    }

    fun excludeFromLb(uuid: String) {
        try {
            Files.write(Paths.get("config/excluded.txt"), uuid.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.APPEND)
        } catch (e: IOException) {
            Wally.logger.logStackTrace(e.stackTrace)
        }
    }
}