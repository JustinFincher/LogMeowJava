package com.FinGameWorks.LogMeow;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

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
//
//    public static String removeSurrogates(String query) {
//        return new String(query.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_16);
//    }

//    public static int symbolRanges[] =
//            {
//                    0x0020, 0x00FF, // Basic Latin + Latin Supplement
//                    0x20AC, 0x20AC, // €
//                    0x2122, 0x2122, // ™
//                    0x2196, 0x2196, // ↖
//                    0x21D6, 0x21D6, // ⇖
//                    0x2B01, 0x2B01, // ⬁
//                    0x2B09, 0x2B09, // ⬉
//                    0x2921, 0x2922, // ⤡ ⤢
//                    0x250C, // ┌
//                    0x2500, // ─
//                    0x2502, // │
//                    0,
//            };

}
