package com.blackoutburst.wally.core.logger

import java.text.SimpleDateFormat
import java.lang.StackTraceElement
import java.io.IOException
import com.blackoutburst.wally.core.Wally
import org.slf4j.helpers.Util
import java.io.File
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.lang.Exception
import java.lang.StringBuilder
import java.nio.file.Files
import java.util.*

object Logger {

    private val dateFormat = SimpleDateFormat("dd/MM - HH:mm:ss")
    val currentLogFile: String

    var internalLogLevel = LogLevel.INFO

    fun logStackTrace(stacktrace: Array<StackTraceElement>) {
        for (stack in stacktrace) log(LogLevel.ERROR, stack.toString())
    }

    init {
        val fileFormat = SimpleDateFormat("dd-MM-yyyy HH-mm-ss")
        val logFolder = File("logs")
        val date = Date()
        if (!logFolder.exists() && !logFolder.mkdir()) {
            log(LogLevel.WARN, "Logs folder creation failed!")
        }
        currentLogFile = "logs/" + fileFormat.format(date) + ".log"
        val logFile = File(currentLogFile)
        try {
            if (!logFile.exists() && !logFile.createNewFile()) {
                log(LogLevel.WARN, "Logs file creation failed!")
            }
        } catch (e: IOException) {
            Wally.logger.logStackTrace(e.stackTrace)
        }
    }

    fun log(level: LogLevel, message: String?) {
        if (level == LogLevel.NONE) return
        if (level.weight < internalLogLevel.weight) return

        val date = Date()
        val className = Util.getCallingClass().simpleName
        val logBuilder = StringBuilder()
            .append("[").append(level)
            .append("] [").append(dateFormat.format(date))
            .append("] [").append(className)
            .append("/").append(Thread.currentThread().name)
            .append("]: ").append(message)
            .append(System.lineSeparator())
        print(logBuilder)

        try {
            Files.write(Paths.get(currentLogFile), logBuilder.toString().toByteArray(), StandardOpenOption.APPEND)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}