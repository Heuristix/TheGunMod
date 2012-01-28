package com.heuristix.util;

import com.heuristix.Mod;
import com.heuristix.Util;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Created by IntelliJ IDEA.
 * User: Matt
 * Date: 1/24/12
 * Time: 9:04 PM
 */
public class Log {

    private static File defaultLog = Util.getHeuristixFile("", "log.txt");

    private static Logger logger;

    private Log() { }

    public static Logger getLogger() {
        if(logger == null) {
            logger = Logger.getLogger(Log.class.toString());
        }
        return logger;
    }

    public static void addHandler(Mod mod) throws IOException {
        addHandler(mod, new SimpleFormatter());
    }

    public static void addHandler(Mod mod, Formatter simpleFormatter) throws IOException {
        ModHandler handler = new ModHandler(mod);
        handler.setFormatter(simpleFormatter);
        getLogger().addHandler(handler);
    }

    public static void fine(Object o, Class<? extends Mod>... mods) {
        log(Level.FINE, o, mods);
    }

    public static void finer(Object o,  Class<? extends Mod>... mods) {
        log(Level.FINER, o, mods);
    }

    public static void finest(Object o,  Class<? extends Mod>... mods) {
        log(Level.FINEST, o, mods);
    }

    public static void severe(Object o,  Class<? extends Mod>... mods) {
        log(Level.SEVERE, o, mods);
    }
    public static void warning(Object o,  Class<? extends Mod>... mods) {
        log(Level.WARNING, o, mods);
    }

    public static void config(Object o,  Class<? extends Mod>... mods) {
        log(Level.CONFIG, o, mods);
    }

    public static void throwing(Class clazz, String method, Throwable t, Class<? extends Mod>... mods) {
        throwing(clazz.getName(), method, t, mods);
    }

    public static void throwing(String clazz, String method, Throwable t,  Class<? extends Mod>... mods) {
        ModLogRecord record = new ModLogRecord(Level.FINE, "THROW", mods);
        record.setSourceClassName(clazz);
        record.setSourceMethodName(method);
        record.setThrown(t);
        log(record);
    }

    public static void log(Level level, Object o,  Class<? extends Mod>... mods) {
        log(level, o, new Object[0], mods);
    }

    public static void log(Level level, Object o, Object[] params,  Class<? extends Mod>... mods) {
        ModLogRecord record = new ModLogRecord(level, o.toString(), mods);
        record.setParameters(params);
        log(record);
    }

    public static void log(LogRecord record) {
        getLogger().log(record);
    }
}
