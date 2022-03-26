package com.blackoutburst.wally.core.logger;

import org.slf4j.helpers.Util;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM - HH:mm:ss");

    private static Logger instance = null;

    private final String currentLogFile;

    /**
     * Get the logger instance
     * @return the logger
     */
    public static @Nonnull Logger getInstance() {
        if (instance == null)
            instance = new Logger();

        return (instance);
    }

    /**
     * Create and initialize the logger
     * Create the log folder if missing and generate a new log file
     */
    private Logger() {
        final SimpleDateFormat fileFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        final File logFolder = new File("logs");
        final Date date = new Date();

        if (!logFolder.exists() && !logFolder.mkdir()) {
            log(LogLevel.WARN,"Logs folder creation failed!");
        }

        currentLogFile = "logs/" + fileFormat.format(date) + ".log";

        final File logFile = new File(currentLogFile);

        try {
            if (!logFile.exists() && !logFile.createNewFile()) {
                log(LogLevel.WARN, "Logs file creation failed!");
            }
        } catch (IOException e) {
            log(LogLevel.ERROR, e.getMessage());
        }
    }

    /**
     * Log message to the console and inside a log file
     * @param level the log level
     * @param message the log message
     */
    public void log(@Nonnull LogLevel level, @Nonnull String message) {
        final Date date = new Date();
        final String className = Util.getCallingClass().getSimpleName();
        final StringBuilder logBuilder = new StringBuilder()
            .append("[").append(level)
            .append("] [").append(dateFormat.format(date))
            .append("] [").append(className)
            .append("/").append(Thread.currentThread().getName())
            .append("]: ").append(message);

        System.out.println(logBuilder);

        try {
            Files.write(Paths.get(currentLogFile), logBuilder.toString().getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
