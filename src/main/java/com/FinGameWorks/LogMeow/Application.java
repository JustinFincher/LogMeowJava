package com.FinGameWorks.LogMeow;


import com.github.cosysoft.device.android.impl.AndroidDeviceStore;
import glm_.vec2.Vec2i;
import glm_.vec4.Vec4;
import imgui.*;
import imgui.impl.ImplGL3;
import imgui.impl.LwjglGlfw;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import uno.glfw.GlfwWindow;
import uno.glfw.windowHint;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Application
{
    private GlfwWindow window;
    private uno.glfw.glfw glfw = uno.glfw.glfw.INSTANCE;
    private LwjglGlfw lwjglGlfw = LwjglGlfw.INSTANCE;
    private ImplGL3 implGL3 = ImplGL3.INSTANCE;
    private Context ctx;
    private ImGui imgui = ImGui.INSTANCE;

    public static void main(String[] args)
    {
        for(String s : args)
        {
            if(s.equals("run"))
            {
                new Application();
                return;
            }
        }
        Restart();
    }

    public static void Restart()
    {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            if (OSUtilities.isMac())
            {
                pb.command("java", "-jar", "-XstartOnFirstThread", Application.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), "run");
            }else
            {
                pb.command("java", "-jar", Application.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), "run");
            }
            pb.inheritIO();
            pb.start();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public Application()
    {
        AdbManager adbManager = AdbManager.INSTANCE;

        glfw.init("4.1", windowHint.Profile.core, true);
        windowHint.INSTANCE.setDecorated(true);
        windowHint.INSTANCE.setResizable(true);
        windowHint.INSTANCE.setTransparentFramebuffer(!OSUtilities.isWindows());

        window = new GlfwWindow(800, 500, "LogMeow", NULL, new Vec2i(Integer.MIN_VALUE), true);
        window.init(true);

        glfw.setSwapInterval(1);

        ctx = new Context(null);
        lwjglGlfw.init(window, true, LwjglGlfw.GlfwClientApi.OpenGL);
        imgui.getIo().getFonts().addFontFromFileTTF("FiraCode-Retina.ttf", 15.0f, new FontConfig(),new int[]{});
        imgui.styleColorsDark(null);
        imgui.getStyle().setFrameRounding(12);
        imgui.getStyle().setFrameBorderSize(1);
        imgui.getStyle().setPopupBorderSize(1);
        imgui.getStyle().setWindowBorderSize(1);
        imgui.captureKeyboardFromApp(true);

        window.setWindowCloseCallback(() -> {
            System.exit(0);
            return Unit.INSTANCE;
        });

        window.loop(() -> {
            mainLoop();
            return Unit.INSTANCE;
        });
        quit();
    }

    public void quit()
    {
        AndroidDeviceStore.getInstance().shutdown();
        lwjglGlfw.shutdown();
        ContextKt.destroy(ctx);
        window.destroy();
        glfw.terminate();
    }


    private Vec4 clearColor = OSUtilities.isWindows() ? new Vec4(0.2f,0.2f,0.2f, 1.0f) : new Vec4(0.0f,0.0f,0.0f, 0.5f);

    private void mainLoop() {

        gln.GlnKt.glViewport(window.getFramebufferSize());
        gln.GlnKt.glClearColor(clearColor);
        glClear(GL_COLOR_BUFFER_BIT);
        lwjglGlfw.newFrame();
        try
        {
            GUIManager.INSTANCE.draw(imgui);
        }
        catch (Exception e)
        {
            LogManager.INSTANCE.logger.warning(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        imgui.render();
        implGL3.renderDrawData(imgui.getDrawData());
    }
}