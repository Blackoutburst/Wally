package com.blackoutburst.wally.core;

import org.apache.logging.log4j.util.StackLocatorUtil;
import org.slf4j.helpers.Util;
import sun.reflect.Reflection;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM - HH:mm:ss");

    public static void info(String message) {
        final Date date = new Date();
        final String className = Util.getCallingClass().getSimpleName();
        System.out.println("[INFO] ["+DATE_FORMAT.format(date)+"] ["+className+"/"+Thread.currentThread().getName()+"]: "+message);
    }

    public static void error(String message) {
        final Date date = new Date();
        final String className = Util.getCallingClass().getSimpleName();
        System.err.println("[ERROR] ["+DATE_FORMAT.format(date)+"] ["+className+"/"+Thread.currentThread().getName()+"]: "+message);
    }

}
