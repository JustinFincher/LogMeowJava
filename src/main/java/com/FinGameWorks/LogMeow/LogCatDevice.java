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
import java.util.stream.Collectors;

public class LogCatDevice
{
    public String serialNum;

    public LogCatDevice(String serial)
    {
        this.serialNum = serial;
        androidDevice = AndroidDeviceStore.getInstance().getDeviceBySerial(serial);
        if (androidDevice != null)
        {
            try {
                androidDevice.addLogCatListener(logCatListener);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else {
            System.out.println("androidDevice null");
        }
    }

    public void stopListening()
    {
        try {
            androidDevice.removeLogCatListener(logCatListener);
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
