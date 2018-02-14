package com.pikachu.uhcessentials.gui;

import com.pikachu.uhcessentials.Main;
import com.pikachu.uhcessentials.gui.elements.base.MovableWindow;
import com.pikachu.uhcessentials.gui.elements.base.Window;
import com.pikachu.uhcessentials.hotkeys.Hotkey;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class OptionScreen extends GuiScreen {

    public static ArrayList<Window> windows = new ArrayList<Window>();

    private OptionScreen optionScreen = this;
    private int lastX;
    private int lastY;
    private int lastButton;

    public OptionScreen() {
        HotkeyStore.add(new Hotkey(this) {
            @Override
            public void onPress() {
                Minecraft.getMinecraft().displayGuiScreen(optionScreen);
            }

            @Override
            public KeyBinding getKeyBinding() {
                return new KeyBinding("Open options screen", Keyboard.KEY_L, "UHC Essentials");
            }
        });
    }

    public boolean hovering(Window window, int x, int y) {
        return Math.abs(y - window.getY()) <= window.getHeight() &&
                (x - window.getX()) <= window.getWidth();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        lastX = mouseX;
        lastY = mouseY;
        lastButton = mouseButton;
        for (Window window : windows) {
            if (hovering(window, mouseX, mouseY)) {
                window.setClicked(true);
                return;
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (Window window : windows) {
            if (hovering(window, mouseX, mouseY) && lastButton == 1) {
                window.toggle();
            }
            Main.getConfig().get(window.getName(), "x", window.getDefaultX(),
                    "Controls the x coordinate of the " + window.getName() + " window").set(window.getX());
            Main.getConfig().get(window.getName(), "y", window.getDefaultY(),
                    "Controls the y coordinate of the " + window.getName() + " window").set(window.getY());
            Main.getConfig().get(window.getName(), "enabled", true,
                    "Controls whether or not the " + window.getName() + " window is enabled").set(window.isEnabled());
            window.setClicked(false);
        }
        Main.getConfig().save();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == HotkeyStore.get(this).binding.getKeyCode()) {
            for (Window window : windows) {
                window.setClicked(false);
            }
            mc.displayGuiScreen(null);
            if (mc.currentScreen == null) {
                mc.setIngameFocus();
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (lastButton == 0) {
            for (Window window : windows) {
                if (window instanceof MovableWindow && window.isClicked()) {
                    window.setX(window.getX() + (mouseX - lastX));
                    window.setY(window.getY() + (mouseY - lastY));
                }
            }
        }
        lastX = mouseX;
        lastY = mouseY;
    }

}
