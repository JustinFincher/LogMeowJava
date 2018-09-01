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
                name = androidDevice.getName();
                serial = androidDevice.getSerialNumber();
                product = properties.get("ro.build.product");
                System.out.println("Device setAndroidDevice " + serial);
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
    public String name = "";
    public String serial = "";
    public String product = "";
    public TreeMap<String, String> properties;
}
