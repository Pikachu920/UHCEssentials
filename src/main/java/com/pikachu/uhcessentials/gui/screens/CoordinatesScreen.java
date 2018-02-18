package com.pikachu.uhcessentials.gui.screens;

import com.pikachu.uhcessentials.hotkeys.Hotkey;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import com.pikachu.uhcessentials.utils.Callback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class CoordinatesScreen extends GuiScreen {

    private GuiTextField xInput;
    private GuiTextField zInput;
    private Callback<BlockPos> callback;
    private CoordinatesScreen instance = this;

    public CoordinatesScreen(Callback<BlockPos> callback) {
        this.callback = callback;
        this.instance = this;
        HotkeyStore.add(new Hotkey(this) {
            @Override
            public void onPress() {
                Minecraft.getMinecraft().displayGuiScreen(instance);
            }

            @Override
            public KeyBinding getKeyBinding() {
                return new KeyBinding("key.uhcessentials.coordinatesmenu", Keyboard.KEY_C, "key.uhcessentials.category");
            }
        });
    }

    @Override
    public void initGui() {
        super.initGui();
        xInput = new GuiTextField(0, fontRendererObj, mc.displayHeight / 2, mc.displayWidth / 4, 100, 20);
        xInput.setFocused(true);
        xInput.setMaxStringLength(15);
        zInput = new GuiTextField(0, fontRendererObj, mc.displayHeight / 2, (mc.displayWidth / 4) * 3, 100, 20);
        zInput.setMaxStringLength(15);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(0);
        xInput.drawTextBox();
        zInput.drawTextBox();
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

}
