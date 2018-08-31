package com.FinGameWorks.LogMeow;

import imgui.ImGui;
import imgui.WindowFlag;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum  GUIManager {
    INSTANCE;

    public boolean[] devicesWindowShown = {true};
    public boolean[] demoWindowShown = {false};
    public void draw(ImGui imgui)
    {
        if (imgui.beginMainMenuBar())
        {
            if (imgui.beginMenu("LogMeow",false))
            {
                imgui.endMenu();
            }
            drawTopLevelMenu(imgui);
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            if (imgui.beginMenu(date,false))
            {
                imgui.endMenu();
            }
            imgui.endMainMenuBar();

        }

        if (devicesWindowShown[0])
        {
            imgui.begin("Devices", devicesWindowShown, WindowFlag.None.getI());
            imgui.end();
        }
        if (demoWindowShown[0])
        {
            imgui.showDemoWindow(demoWindowShown);
        }
        if (imgui.getIo().getMouseClicked()[1])
        {
            imgui.openPopup("blank_scene_context_menu");
        }
        if (imgui.beginPopup("blank_scene_context_menu",0))
        {
            drawTopLevelMenu(imgui);
            imgui.endPopup();
        }

    }

    private void drawTopLevelMenu(ImGui imgui)
    {
        if (imgui.beginMenu("Widgets",true))
        {
            boolean isMenuDeviceItemSelected;
            isMenuDeviceItemSelected = imgui.menuItem( "Devices","D", devicesWindowShown[0],true);
            if (isMenuDeviceItemSelected)
            {
                devicesWindowShown[0] = !devicesWindowShown[0];
            }
            imgui.endMenu();
        }
        if (imgui.beginMenu("Metrics",true))
        {
            boolean isMenuDemoItemSelected;
            isMenuDemoItemSelected = imgui.menuItem( "IMGUI Demo","", demoWindowShown[0],true);
            if (isMenuDemoItemSelected)
            {
                demoWindowShown[0] = !demoWindowShown[0];
            }
            imgui.endMenu();
        }
    }
}