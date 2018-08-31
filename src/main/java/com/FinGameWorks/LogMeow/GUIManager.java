package com.FinGameWorks.LogMeow;

import glm_.vec2.Vec2;
import glm_.vec4.Vec4;
import imgui.*;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

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
//            String fps = String.format("%.3f ms | %.1f FPS", 1_000f / imgui.getIo().getFramerate(), imgui.getIo().getFramerate());
//            if (imgui.beginMenu(fps, false))
//            {
//                imgui.endMenu();
//            }
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            if (imgui.beginMenu(date,false))
            {
                imgui.endMenu();
            }
            imgui.endMainMenuBar();
        }

        if (devicesWindowShown[0])
        {
            drawDeviceWindow(imgui);
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

    private void drawDeviceWindow(ImGui imgui)
    {
        imgui.begin("Devices", devicesWindowShown, WindowFlag.MenuBar.getI());

        if(imgui.beginMenuBar())
        {
            imgui.text("Device Count: " + AdbManager.INSTANCE.devices.size());
            imgui.endMenuBar();
        }
        imgui.columns(6, "devices", true);
        imgui.separator();
        imgui.text("Serial"); imgui.nextColumn();
        imgui.text("Brand"); imgui.nextColumn();
        imgui.text("Model"); imgui.nextColumn();
        imgui.text("OS"); imgui.nextColumn();
        imgui.text("State"); imgui.nextColumn();
        imgui.text("Extras"); imgui.nextColumn();
        imgui.separator();


        AdbManager.INSTANCE.devices.stream().forEach(device ->
        {
            boolean hovered = false;
            imgui.text(device.serial); imgui.nextColumn();
            imgui.text(device.brand); imgui.nextColumn();
            imgui.text(device.model); imgui.nextColumn();
            imgui.text(device.osVersion + " (API " + device.apiLevel + ")"); imgui.nextColumn();
            imgui.text(device.state); imgui.nextColumn();
            imgui.text("  ");
            hovered = hovered || imgui.isItemHovered(HoveredFlag.AnyWindow);
            imgui.nextColumn();
            if (hovered)
            {
                boolean[] hoveredDetailWindowShown = {hovered};
                imgui.setNextWindowPos(imgui.getMousePos(),Cond.Always,new Vec2(-0.02,-0.02));
                imgui.setNextWindowBgAlpha(0.4f);
                imgui.setNextWindowFocus();
                if (imgui.begin("getprop", hoveredDetailWindowShown, WindowFlag.NoSavedSettings.getI() | WindowFlag.AlwaysAutoResize.getI()))
                {
                    imgui.pushStyleColor(Col.Text, new Vec4(1.0f,1.0f,1.0f, 0.4f));
                    imgui.text(device.allProp);
                    imgui.popStyleColor(1);
                    imgui.end();
                }
            }

            imgui.separator();
        });
        imgui.end();
    }
}
