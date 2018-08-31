package com.FinGameWorks.LogMeow;


import java.util.logging.Logger;

public enum LogManager {
    INSTANCE;

    public Logger logger = Logger.getLogger("LogMeow");
}