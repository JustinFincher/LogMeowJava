package com.FinGameWorks.LogMeow;

import com.android.ddmlib.logcat.LogCatMessage;

import java.util.*;
import java.util.function.Consumer;

public enum  LogCatManager {
    INSTANCE;

    public void tryDiffSerial(List<String> serialList)
    {
        logCatDeviceMap.keySet().stream().forEach(key -> System.out.println("LogDevice " + key));
//        serialLogsMap.keySet().stream().forEach(key -> System.out.println("Logs " + key));

        serialList.stream().forEach(serial ->
        {
            if (!logCatDeviceMap.containsKey(serial))
            {
                System.out.println("!logCatDeviceMap containsKey ." + serial + ".");
                LogCatDevice logCatDevice = new LogCatDevice(serial);
                if (logCatDeviceMap.put(serial, logCatDevice) == null)
                {
                    System.out.println("!logCatDeviceMap put = null");
                }
            }
            if (!serialLogsMap.containsKey(serial))
            {
                LogManager.INSTANCE.logger.info("!serialLogsMap containsKey " + serial);
                serialLogsMap.put(serial,new ArrayList<LogCatMessage>());
            }
        });
        logCatDeviceMap.keySet().stream().forEach(oldSerial -> {
            if (!serialList.contains(oldSerial))
            {
                System.out.println("logCatDeviceMap remove " + oldSerial);
                //remove old serial
                try {
                    logCatDeviceMap.get(oldSerial).stopListening();
                    logCatDeviceMap.remove(oldSerial);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

    public void addLog(String serial,LogCatMessage message)
    {
//        System.out.println(message.getMessage());
        if (!serialLogsMap.containsKey(serial))
        {
            LogManager.INSTANCE.logger.info("serialLogsMap add " + serial);
            serialLogsMap.put(serial, new ArrayList<LogCatMessage>());
        }
        ArrayList<LogCatMessage> list = serialLogsMap.putIfAbsent(serial, new ArrayList<LogCatMessage>());
        if (list != null)
        {
            list.add(message);
            if (list.size() > 1000)
            {
                list.subList(0, (list.size() - 1000)).clear();
            }
        }
    }

    public HashMap<String,LogCatDevice> logCatDeviceMap = new HashMap<>();
    public HashMap<String, ArrayList<LogCatMessage>> serialLogsMap = new HashMap<>();
}
