package com.FinGameWorks.LogMeow;


import glm_.vec2.Vec2i;
import glm_.vec4.Vec4;
import imgui.*;
import imgui.impl.ImplGL3;
import imgui.impl.LwjglGlfw;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import uno.glfw.GlfwWindow;
import uno.glfw.windowHint;

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
        new Application();
    }

    public Application()
    {
        glfw.init("4.1", windowHint.Profile.core, true);
        windowHint.INSTANCE.setDecorated(true);
        windowHint.INSTANCE.setResizable(true);
        windowHint.INSTANCE.setAutoIconify(true);
        windowHint.INSTANCE.setTransparentFramebuffer(true);

        window = new GlfwWindow(800, 500, "LogMeow", NULL, new Vec2i(Integer.MIN_VALUE), true);
        window.init(true);

        glfw.setSwapInterval(1);

        ctx = new Context(null);
        lwjglGlfw.init(window, true, LwjglGlfw.GlfwClientApi.OpenGL);
        imgui.getIo().getFonts().addFontFromFileTTF("FiraCode-Retina.ttf", 16.0f, new FontConfig(),new int[]{});
        imgui.styleColorsDark(null);
        imgui.getStyle().setFrameRounding(12);
        imgui.getStyle().setFrameBorderSize(1);
        imgui.getStyle().setPopupBorderSize(1);
        imgui.getStyle().setWindowBorderSize(1);


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
        lwjglGlfw.shutdown();
        ContextKt.destroy(ctx);
        window.destroy();
        glfw.terminate();
    }


    private Vec4 clearColor = new Vec4(0.0f,0.0f,0.0f, 0.5f);

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