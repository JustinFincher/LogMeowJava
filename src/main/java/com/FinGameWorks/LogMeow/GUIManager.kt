package com.FinGameWorks.LogMeow

import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.*
import imgui.internal.Window

import java.text.SimpleDateFormat
import java.util.Date

enum class GUIManager {
    INSTANCE;

    var devicesWindowShown = booleanArrayOf(true)
    var logcatWindowShown = booleanArrayOf(false)
    var demoWindowShown = booleanArrayOf(false)
    var currentGetPropDevice: Device? = null

    fun draw(imgui: ImGui) {
        if (imgui.beginMainMenuBar()) {
            if (imgui.beginMenu("LogMeow", false)) {
                imgui.endMenu()
            }
            drawTopLevelMenu(imgui)
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            if (imgui.beginMenu(date, false)) {
                imgui.endMenu()
            }
            imgui.endMainMenuBar()
        }

        if (devicesWindowShown[0]) {
            drawDeviceWindow(imgui)
        }
        if (logcatWindowShown[0]) {
            drawLogcatWindow(imgui)
        }
        if (demoWindowShown[0]) {
            imgui.showDemoWindow(demoWindowShown)
        }
        if (imgui.io.mouseClicked[1]) {
            imgui.openPopup("blank_scene_context_menu")
        }
        if (imgui.beginPopup("blank_scene_context_menu", WindowFlag.None.i)) {
            drawTopLevelMenu(imgui)
            imgui.endPopup()
        }
    }

    private fun drawTopLevelMenu(imgui: ImGui) {
        if (imgui.beginMenu("Widgets", true)) {
            val isMenuDeviceItemSelected: Boolean = imgui.menuItem("Devices", "D", devicesWindowShown[0], true)
            if (isMenuDeviceItemSelected) {
                devicesWindowShown[0] = !devicesWindowShown[0]
            }
            val isMenuLogcatItemSelected: Boolean = imgui.menuItem("Logcat", "L", logcatWindowShown[0], true)
            if (isMenuLogcatItemSelected) {
                logcatWindowShown[0] = !logcatWindowShown[0]
            }
            imgui.endMenu()
        }
        if (imgui.beginMenu("Metrics", true)) {
            val isMenuDemoItemSelected: Boolean = imgui.menuItem("IMGUI Demo", "", demoWindowShown[0], true)
            if (isMenuDemoItemSelected) {
                demoWindowShown[0] = !demoWindowShown[0]
            }
            imgui.endMenu()
        }
    }

    private fun drawDeviceWindow(imgui: ImGui) {
        imgui.begin("Devices", devicesWindowShown, WindowFlag.MenuBar.i)

        var window : Window = imgui.currentWindow
        if (imgui.beginMenuBar()) {
            drawWindowSizePosAdjustMenu(imgui,window)
            imgui.text("Device Count: " + AdbManager.INSTANCE.devices.size)
            imgui.endMenuBar()
        }
        imgui.columns(6, "devices", true)
        imgui.separator()
        imgui.text("Serial")
        imgui.nextColumn()
        imgui.text("Brand")
        imgui.nextColumn()
        imgui.text("Model")
        imgui.nextColumn()
        imgui.text("OS")
        imgui.nextColumn()
        imgui.text("State")
        imgui.nextColumn()
        imgui.text("Extras")
        imgui.nextColumn()
        imgui.separator()


        AdbManager.INSTANCE.devices.stream().forEach { device ->
            var hovered = false
            var clicked = false
            imgui.text(device.serial)
            imgui.nextColumn()
            imgui.text(device.brand)
            imgui.nextColumn()
            imgui.text(device.model)
            imgui.nextColumn()
            imgui.text(device.osVersion + " (API " + device.apiLevel + ")")
            imgui.nextColumn()
            imgui.text(device.state)
            imgui.nextColumn()
            if (imgui.button("getprop")) {
                currentGetPropDevice = device;
                clicked = true
            }
            hovered = imgui.isItemHovered(HoveredFlag.AnyWindow)
            imgui.nextColumn()
            if (hovered) {
                val hoveredDetailWindowShown = booleanArrayOf(hovered)
                imgui.setNextWindowPos(imgui.mousePos, Cond.Always, Vec2(-0.02, -0.02))
                imgui.setNextWindowBgAlpha(0.4f)
                imgui.setNextWindowFocus()
                if (imgui.begin("getprop", hoveredDetailWindowShown, WindowFlag.NoSavedSettings.i or WindowFlag.AlwaysAutoResize.i)) {
                    drawDeviceDetailProp(device, imgui)
                    imgui.end()
                }
            }
            if (clicked) {
                imgui.openPopup("get_prop")
            }
            imgui.separator()
        }
        imgui.setNextWindowSize(Vec2(imgui.io.displaySize.x/2,imgui.io.displaySize.y/2))
        imgui.setNextWindowPos(Vec2(imgui.io.displaySize.x/4,imgui.io.displaySize.y/4))
        if (imgui.beginPopupModal("get_prop", null, WindowFlag.NoResize.i)) {
            drawDeviceDetailProp(currentGetPropDevice, imgui)
            if (imgui.button("Close"))
            {
                imgui.closeCurrentPopup()
            }
            imgui.endPopup()
        }
        imgui.end()
    }

    private fun drawLogcatWindow(imgui: ImGui)
    {
        imgui.begin("Logcat", logcatWindowShown, WindowFlag.MenuBar.i)

        var window : Window = imgui.currentWindow
        if (imgui.beginMenuBar()) {
            drawWindowSizePosAdjustMenu(imgui,window)
            imgui.endMenuBar()
        }
        imgui.end();
    }

    fun drawDeviceDetailProp(device: Device?, imgui: ImGui) {
        imgui.pushStyleColor(Col.Text, Vec4(1.0f, 1.0f, 1.0f, 0.4f))
        if (device != null)
        {
            imgui.text(device.allProp)
        }
        imgui.popStyleColor(1)
    }

    fun drawWindowSizePosAdjustMenu(imgui: ImGui, window: Window)
    {
        var barHeight = 20
        var windowEdgePadding = 10
        if (imgui.beginMenu("Window", true)) {

            val isMenuCompactScreenItemSelected: Boolean = imgui.menuItem("Compact Window","OPTION+C");
            if (isMenuCompactScreenItemSelected)
            {
                imgui.setWindowSize(window.name,Vec2(300,160))
            }
            val isMenuLeftScreenItemSelected: Boolean = imgui.menuItem("Left Screen","OPTION+L");
            if (isMenuLeftScreenItemSelected)
            {
                imgui.setWindowSize(window.name,Vec2(imgui.io.displaySize.x / 2 - windowEdgePadding * 2,imgui.io.displaySize.y - windowEdgePadding * 2 - barHeight))
                imgui.setWindowPos(window.name,Vec2(windowEdgePadding,windowEdgePadding + barHeight))
            }
            val isMenuRightScreenItemSelected: Boolean = imgui.menuItem("Right Screen","OPTION+R");
            if (isMenuRightScreenItemSelected)
            {
                imgui.setWindowSize(window.name,Vec2(imgui.io.displaySize.x / 2 - windowEdgePadding * 2,imgui.io.displaySize.y - windowEdgePadding * 2 - barHeight))
                imgui.setWindowPos(window.name,Vec2(imgui.io.displaySize.x / 2 + windowEdgePadding,windowEdgePadding + barHeight))
            }
            val isMenuTopScreenItemSelected: Boolean = imgui.menuItem("Top Screen","OPTION+T");
            if (isMenuTopScreenItemSelected)
            {
                imgui.setWindowSize(window.name,Vec2(imgui.io.displaySize.x- windowEdgePadding * 2,(imgui.io.displaySize.y - barHeight) / 2 - windowEdgePadding * 2))
                imgui.setWindowPos(window.name,Vec2(windowEdgePadding, windowEdgePadding + barHeight))
            }
            val isMenuBottomScreenItemSelected: Boolean = imgui.menuItem("Bottom Screen","OPTION+B");
            if (isMenuBottomScreenItemSelected)
            {
                imgui.setWindowSize(window.name,Vec2(imgui.io.displaySize.x- windowEdgePadding * 2,(imgui.io.displaySize.y - barHeight) / 2 - windowEdgePadding * 2))
                imgui.setWindowPos(window.name,Vec2(windowEdgePadding,(imgui.io.displaySize.y - barHeight) / 2 +barHeight + windowEdgePadding))
            }
            val isMenuFullScreenItemSelected: Boolean = imgui.menuItem("Full Screen","OPTION+F");
            if (isMenuFullScreenItemSelected)
            {
                imgui.setWindowSize(window.name,Vec2(imgui.io.displaySize.x-windowEdgePadding*2,imgui.io.displaySize.y-windowEdgePadding*2-barHeight))
                imgui.setWindowPos(window.name,Vec2(windowEdgePadding,windowEdgePadding + barHeight))
            }
            imgui.endMenu()
        }
    }
}
