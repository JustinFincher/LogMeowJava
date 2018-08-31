package com.FinGameWorks.LogMeow;

public class OSUtilities {
    public static boolean isMac()
    {
        return System.getProperty("os.name").startsWith("Mac");
    }

    public static boolean isWindows()
    {
        return System.getProperty("os.name").startsWith("Windows");
    }
}
