package com.FinGameWorks.LogMeow;

import com.android.ddmlib.Log;
import com.android.ddmlib.logcat.LogCatFilter;
import com.android.ddmlib.logcat.LogCatListener;
import com.android.ddmlib.logcat.LogCatMessage;
import com.github.cosysoft.device.android.AndroidDevice;
import com.github.cosysoft.device.android.impl.AndroidDeviceStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class LogCatDevice
{
    public String serialNum;

    public LogCatDevice(String serial)
    {
        System.out.println("LogCatDevice " + serial + " init");
        this.serialNum = serial;

        try {
            androidDevice = AndroidDeviceStore.getInstance().getDeviceBySerial(serial);
            System.out.println("androidDevice " + androidDevice);
            if (androidDevice != null)
            {
                Runnable r = () -> {
                    androidDevice.addLogCatListener(logCatListener);
                };

                ExecutorService executor = Executors.newCachedThreadPool();
                executor.submit(r);

            }
            else {
                System.out.println("androidDevice null");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stopListening()
    {
        try {
            Runnable r = () -> {
                androidDevice.removeLogCatListener(logCatListener);
            };

            ExecutorService executor = Executors.newCachedThreadPool();
            executor.submit(r);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public AndroidDevice androidDevice;
//    public LogCatFilter filter = new LogCatFilter("", "", "", "",
//                "Skyline", Log.LogLevel.DEBUG);

    private final LogCatListener logCatListener = msgList -> {
        for (LogCatMessage msg : msgList)
        {
           LogCatManager.INSTANCE.addLog(serialNum, msg);
        }
    };
}
