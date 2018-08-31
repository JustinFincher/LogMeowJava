package com.FinGameWorks.LogMeow;

import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Device {
    public void setJadbDevice(JadbDevice jadbDevice) {
        this.jadbDevice = jadbDevice;
        if (jadbDevice != null)
        {
            try {
                InputStream inputStream;


                inputStream = this.jadbDevice.execute("getprop");
                String allPropStr = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining("\n"));
                inputStream.close();
                allProp = allPropStr != null ? allPropStr : "";

                inputStream = this.jadbDevice.execute("getprop","ro.product.model");
                String modelStr = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining("\n"));
                inputStream.close();
                model = modelStr != null ? modelStr : "";

                inputStream = this.jadbDevice.execute("getprop", "ro.product.name");
                String nameStr = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining("\n"));
                inputStream.close();
                name = nameStr != null ? nameStr : "";

                inputStream = this.jadbDevice.execute("getprop", "ro.product.brand");
                String brandStr = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining("\n"));
                inputStream.close();
                brand = brandStr != null ? brandStr : "";

                inputStream = this.jadbDevice.execute("getprop", "ro.build.version.release");
                String osVersionStr = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining("\n"));
                inputStream.close();
                osVersion = osVersionStr != null ? osVersionStr : "";

                inputStream = this.jadbDevice.execute("getprop", "ro.build.version.sdk");
                String apiLevelStr = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining("\n"));
                inputStream.close();
                apiLevel = apiLevelStr != null ? apiLevelStr : "";

                serial = jadbDevice.getSerial();
                state = jadbDevice.getState().name();

            } catch (IOException | JadbException e) {
                e.printStackTrace();
            }
        }
    }

    public JadbDevice jadbDevice;
    public String model = "";
    public String name = "";
    public String brand = "";
    public String osVersion = "";
    public String apiLevel = "";
    public String serial = "";
    public String state = "";

    public String allProp = "";
}
