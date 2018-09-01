package com.FinGameWorks.LogMeow;

import com.github.cosysoft.device.android.AndroidDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class Device
{
    public void setAndroidDevice(AndroidDevice ad)
    {
        this.androidDevice = ad;
        if (androidDevice != null)
        {
            properties = new TreeMap<>(androidDevice.getDevice().getProperties());

            try {
//                model = androidDevice.getBrand().getModel();
//                brand = androidDevice.getBrand().getManufacture();
                name = androidDevice.getName();
//                osVersion = androidDevice.getDevice().getVersion().toString();
//                apiLevel =androidDevice.getDevice().getVersion().getApiString();
                serial = androidDevice.getSerialNumber();
//                abiList = androidDevice.getDevice().getAbis();
//                allProp = androidDevice.runAdbCommand("shell getprop");
//                this.logCatDevice = new LogCatDevice(this);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else
        {
            LogManager.INSTANCE.logger.warning("androidDevice = null");
        }
    }

    public AndroidDevice getAndroidDevice() {
        return androidDevice;
    }

    private AndroidDevice androidDevice;
    public String model = "";
    public String name = "";
    public String brand = "";
    public String osVersion = "";
    public String apiLevel = "";
    public String serial = "";
    public String state = "";
    public List<String> abiList = new ArrayList<>();
    public TreeMap<String, String> properties;
    public LogCatDevice logCatDevice;
}
