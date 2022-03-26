package com.blackoutburst.wally.core;

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

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM - HH:mm:ss");

    private static boolean isInitialized = false;

    private static String currentLogFile = null;

    public static void init() {
        if (isInitialized) return;
        isInitialized = true;

        final SimpleDateFormat fileFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        final File logFolder = new File("logs");
        final Date date = new Date();

        if (!logFolder.exists()) {
            if (!logFolder.mkdir()) {
                warn("Logs folder creation failed!");
            }
        }

        currentLogFile = "logs/"+fileFormat.format(date)+".log";

        final File logFile = new File(currentLogFile);

        try {
            if (!logFile.exists()) {
                if (!logFile.createNewFile()) {
                    warn("Logs file creation failed!");
                }
            }
        } catch (IOException e) {
            error(e.getMessage());
        }
    }

    public static void info(@Nonnull String message) {
        final Date date = new Date();
        final String className = Util.getCallingClass().getSimpleName();
        final String log = "[INFO] ["+DATE_FORMAT.format(date)+"] ["+className+"/"+Thread.currentThread().getName()+"]: "+message;

        System.out.println(log);

        try {
            Files.write(Paths.get(currentLogFile), log.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void warn(@Nonnull String message) {
        final Date date = new Date();
        final String className = Util.getCallingClass().getSimpleName();
        final String log = "[WARN] ["+DATE_FORMAT.format(date)+"] ["+className+"/"+Thread.currentThread().getName()+"]: "+message;

        System.out.println(log);

        try {
            Files.write(Paths.get(currentLogFile), log.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void error(@Nonnull String message) {
        final Date date = new Date();
        final String className = Util.getCallingClass().getSimpleName();
        final String log = "[ERROR] ["+DATE_FORMAT.format(date)+"] ["+className+"/"+Thread.currentThread().getName()+"]: "+message;

        System.err.println(log);

        try {
            Files.write(Paths.get(currentLogFile), log.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
