package com.FinGameWorks.LogMeow;

import se.vidstige.jadb.JadbException;

import java.io.IOException;
import java.io.InputStream;

public enum  LogCatManager {
    INSTANCE;

    LogCatManager()
    {

    }

    public InputStream getLogs(Device device)
    {
        InputStream inputStream = null;
        try {
            inputStream = AdbManager.INSTANCE.jadb.getDevices().get(0).execute("shell", "logcat -s " + device.serial );
        } catch (IOException | JadbException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
