package com.FinGameWorks.LogMeow;

import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public enum  AdbManager {
    INSTANCE;

    public List<JadbDevice> devices;
    public JadbConnection jadb;
    private Timer timer = new Timer();

    AdbManager() {
        jadb = new JadbConnection();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                if (jadb != null)
                {
                    try {
                        devices = jadb.getDevices();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JadbException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, 2*1000);
    }
}
