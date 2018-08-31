package com.FinGameWorks.LogMeow;

import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum  AdbManager {
    INSTANCE;

    public List<Device> devices;
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
                        devices = jadb.getDevices().stream().map(jadbDevice ->
                        {
                            Device device = new Device();
                            device.setJadbDevice(jadbDevice);
                            return device;
                        }).collect(Collectors.toList());
                    } catch (IOException | JadbException e) {
                        e.printStackTrace();
                        startADB();
                    }
                }
            }
        }, 0, 2*1000);
    }

    private void startADB()
    {
        boolean issue = false;
        try {
            String userPath = System.getenv("PATH");
            LogManager.INSTANCE.logger.info(userPath);
            String[] command = OSUtilities.isWindows() ? new String[]{"cmd.exe", "/c", "adb start-server"} : new String[]{"/bin/bash", "-c", "-l", "adb start-server"};
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            pb.start();
        } catch (IOException e1) {
            e1.printStackTrace();
            issue = true;
        }finally {
            if (!issue)
            {
                Application.Restart();
            }
        }
    }
}
