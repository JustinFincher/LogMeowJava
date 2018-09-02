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

    public static boolean isUnix()
    {
        return System.getProperty("os.name").contains("nix") || System.getProperty("os.name").contains("nux") || System.getProperty("os.name").indexOf("aix") > 0;
    }

    public static void openURL(String url)
    {
        Runtime rt = Runtime.getRuntime();
        try {
            if (isWindows())
            {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url).waitFor();
            } else if (isMac()) {
                String[] cmd = {"open", url};
                rt.exec(cmd).waitFor();
            } else if (isUnix()) {
                String[] cmd = {"xdg-open", url};
                rt.exec(cmd).waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
