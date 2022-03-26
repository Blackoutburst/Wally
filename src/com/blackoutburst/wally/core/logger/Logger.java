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

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();

        return (instance);
    }

    private Logger() {
        final SimpleDateFormat fileFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        final File logFolder = new File("logs");
        final Date date = new Date();

        if (!logFolder.exists()) {
            if (!logFolder.mkdir()) {
                log(LogLevel.WARN,"Logs folder creation failed!");
            }
        }

        currentLogFile = "logs/"+fileFormat.format(date)+".log";

        final File logFile = new File(currentLogFile);

        try {
            if (!logFile.exists()) {
                if (!logFile.createNewFile()) {
                    log(LogLevel.WARN, "Logs file creation failed!");
                }
            }
        } catch (IOException e) {
            log(LogLevel.ERROR, e.getMessage());
        }
    }

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
