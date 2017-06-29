package me.xaanit.simplelogger.exceptions;


public class LoggerNotFoundException extends RuntimeException {

    private Class c;

    public LoggerNotFoundException(Class c) {
        super("Could not find the logger for class " + c.getName());
        this.c = c;
    }

    public LoggerNotFoundException(String str) {
        super("Could not find the logger for the name " + str);
        this.c = null;
    }

    @SuppressWarnings("unused")
    public Class getMissingClass() {
        return c;
    }

}
