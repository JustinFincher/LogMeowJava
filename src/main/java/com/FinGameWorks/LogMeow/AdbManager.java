package com.FinGameWorks.LogMeow;

import com.github.cosysoft.device.android.AndroidDevice;
import com.github.cosysoft.device.android.impl.AndroidDeviceStore;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum  AdbManager {
    INSTANCE;

    public List<Device> devices = new ArrayList<>();
    private Timer timer = new Timer();

    AdbManager()
    {
        try {
            AndroidDeviceStore.getInstance().initAndroidDevices(true);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run()
            {
                new Thread(() -> {
                    TreeSet<AndroidDevice> devicesTreeSet = AndroidDeviceStore.getInstance()
                            .getDevices();
                    devices = devicesTreeSet.stream().map(androidDevice ->
                    {
                        Device device = new Device();
                        device.setAndroidDevice(androidDevice);
                        return device;
                    }).collect(Collectors.toList());

                    LogCatManager.INSTANCE.tryDiffSerial(devices.stream().map(device -> device.serial).collect(Collectors.toList()));

                }).start();
            }
        }, 0, 2000);
    }

    public void shutDown()
    {
        AndroidDeviceStore.getInstance().shutdown();
    }

//    private void startADB()
//    {
//        boolean issue = false;
//        try {
//            String userPath = System.getenv("PATH");
//            LogManager.INSTANCE.logger.info(userPath);
//            String[] command = OSUtilities.isWindows() ? new String[]{"cmd.exe", "/c", "adb start-server"} : new String[]{"/bin/bash", "-c", "-l", "adb start-server"};
//            ProcessBuilder pb = new ProcessBuilder(command);
//            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
//            pb.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//            issue = true;
//        }finally {
//            if (!issue)
//            {
//                Application.Restart();
//            }
//        }
//    }
}
