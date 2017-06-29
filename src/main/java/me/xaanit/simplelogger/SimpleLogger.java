package me.xaanit.simplelogger;

import me.xaanit.simplelogger.exceptions.LoggerNotFoundException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("all")
public class SimpleLogger {

    private static Map<Class, SimpleLogger> loggers = new HashMap<>();
    private Class c;
    private ZoneId id;

    public SimpleLogger(Class c, ZoneId id) {
        loggers.putIfAbsent(c, this);
        this.c = c;
    }

    public SimpleLogger(Class c) {
        this(c, ZoneId.systemDefault());
    }

    public SimpleLogger(ZoneId id) {
        this(SimpleLogger.class, id);
    }

    public SimpleLogger() {
        this(SimpleLogger.class, ZoneId.systemDefault());
    }

    public static SimpleLogger getLoggerByClass(Class c) {
        if (!loggers.containsKey(c))
            throw new LoggerNotFoundException(c);
        return loggers.get(c);
    }

    public static SimpleLogger getLoggerByName(String name) {
        for (Class c : loggers.keySet())
            if (c.getName().equals(name))
                return loggers.get(c);
        throw new LoggerNotFoundException(name);
    }

    private void log(CharSequence str, Level level) {
        if (str == null)
            str = "null";
        switch (level) {
            case FATAL:
            case CRITICAL:
            case HIGH:
            case MEDIUM:
            case LOW:
                System.err.println("[" + getCurrentTime(id) + "] [" + c.getSimpleName() + "] " + level.toString() + ": " + str);
                break;
            case DEBUG:
            case INFO:
                System.out.println("[" + getCurrentTime(id) + "] [" + c.getSimpleName() + "] " + level.toString() + ": " + str);
                break;

        }
    }

    public void fatal(CharSequence str) {
        log(str, Level.FATAL);
    }

    public void critical(CharSequence str) {
        log(str, Level.CRITICAL);
    }

    public void high(CharSequence str) {
        log(str, Level.HIGH);
    }

    public void medium(CharSequence str) {
        log(str, Level.MEDIUM);
    }

    public void low(CharSequence str) {
        log(str, Level.LOW);
    }

    public void debug(CharSequence str) {
        log(str, Level.DEBUG);
    }

    public void info(CharSequence str) {
        log(str, Level.INFO);
    }


    /**
     * Gets the current time in the specified zone ID
     *
     * @return The current time in the zone ID
     */
    private String getCurrentTime(ZoneId id) {
        if(id == null)
            return getCurrentTime();
        LocalDateTime date = LocalDateTime.now(id);
        LocalTime time = LocalTime.now(id);
        return (date.getDayOfWeek().toString().charAt(0)
                + date.getDayOfWeek().toString().substring(1).toLowerCase())
                + ", "
                + (date.getMonth().toString().charAt(0)
                + date.getMonth().toString().substring(1).toLowerCase())
                + " " + date.getDayOfMonth() + " " + date.getYear() + " | "
                + (time.getHour() > 12 ? time.getHour() - 12 : time) + ":" + time.getMinute() + ":"
                + time.getSecond() + (time.getHour() > 12 ? " PM" : " AM");
    }

    /**
     * Gets the current time in UTC
     *
     * @return The current time in UTC
     */
    private String getCurrentTime() {
        LocalDateTime date = LocalDateTime.now();
        LocalTime time = LocalTime.now(Clock.systemUTC());
        return (date.getDayOfWeek().toString().charAt(0)
                + date.getDayOfWeek().toString().substring(1).toLowerCase())
                + ", "
                + (date.getMonth().toString().charAt(0)
                + date.getMonth().toString().substring(1).toLowerCase())
                + " " + date.getDayOfMonth() + " " + date.getYear() + " | "
                + (time.getHour() > 12 ? time.getHour() - 12 : time) + ":" + time.getMinute() + ":"
                + time.getSecond() + (time.getHour() > 12 ? " PM" : " AM");
    }

    private enum Level {
        FATAL,
        CRITICAL,
        HIGH,
        MEDIUM,
        LOW,
        INFO,
        DEBUG
    }
}
