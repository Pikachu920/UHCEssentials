package com.pikachu.uhcessentials.gui.screens;

import com.pikachu.uhcessentials.UHCEssentials;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import com.pikachu.uhcessentials.gui.base.Window;
import com.pikachu.uhcessentials.hotkeys.Hotkey;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import com.pikachu.uhcessentials.utils.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class OptionScreen extends GuiScreen {

    public static ArrayList<Window> windows = new ArrayList<>();

    private int lastX;
    private int lastY;
    private int lastButton;

    public OptionScreen() {
        HotkeyStore.add(new Hotkey(this) {
            @Override
            public void onPress() {
                Minecraft.getMinecraft().displayGuiScreen(UHCEssentials.getOptionScreen());
            }

            @Override
            public KeyBinding getKeyBinding() {
                return new KeyBinding("key.uhcessentials.optionsmenu", Keyboard.KEY_M, "key.uhcessentials.category");
            }
        });
    }

    public boolean hovering(Window window, int x, int y) {
        // harvard offered me a full ride after i wrote this
        return (y >= window.getY() && y <= (window.getY() + window.getHeight())) &&
                ((window.getX() + window.getWidth() >= x) && x >= window.getX());
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (Window window : windows) {
            if (hovering(window, mouseX, mouseY) && lastButton == 1) {
                window.toggle();
            }
            window.save(false);
            window.setClicked(false);
        }
        if (UHCEssentials.getConfig().hasChanged()) {
            UHCEssentials.getConfig().save();
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == HotkeyStore.get(this).binding.getKeyCode()) {
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

    @Override
    public void onGuiClosed() {
        windows.forEach(w -> w.setClicked(false));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        lastX = mouseX;
        lastY = mouseY;
        lastButton = mouseButton;
        for (Window window : Util.reverse(windows)) { // the reversal ensures that top windows are prioritized
            if (hovering(window, mouseX, mouseY)) {
                window.setClicked(true);
                return;
            }
        }
    }

}
