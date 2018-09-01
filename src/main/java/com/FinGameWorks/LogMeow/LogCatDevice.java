package com.FinGameWorks.LogMeow;

import com.android.ddmlib.logcat.LogCatListener;
import com.android.ddmlib.logcat.LogCatMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogCatDevice
{
    public LogCatDevice(Device deviceToSet)
    {
        device = deviceToSet;
        device.getAndroidDevice().addLogCatListener(logCatListener);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        device.getAndroidDevice().removeLogCatListener(logCatListener);
    }

    public Device device;
    public List<LogCatMessage> logs = new ArrayList<>();

    //        final LogCatFilter filter = new LogCatFilter("", "", "com.android", "",
//                "", Log.LogLevel.WARN);

    final LogCatListener logCatListener = msgList -> {
        for (LogCatMessage msg : msgList)
        {
//                    if (filter.matches(msg)) {
            logs.add(msg);
            System.out.println(msg);
//                    }

        }
        if (logs.size() > 1000)
        {
            logs = logs.stream().skip(logs.size() - 1000).collect(Collectors.toList());
        }
    };
}
