package com.pikachu.uhcessentials.gui.screens;

import com.pikachu.uhcessentials.hotkeys.Hotkey;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import com.pikachu.uhcessentials.utils.Callback;
import com.pikachu.uhcessentials.utils.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class CoordinatesScreen extends GuiScreen {

    private GuiTextField xInput;
    private GuiTextField zInput;
    private Callback<BlockPos> callback;
    private CoordinatesScreen instance = this;
    private String title = I18n.format("uhcessentials.coordinatesmenutitle");
    private String error = I18n.format("uhcessentials.invalidcoordinates");
    private String okButtonLabel = I18n.format("uhcessentials.savecoordinateslabel");
    private double xValue;
    private double zValue;
    private GuiButton okButton;

    public CoordinatesScreen(Callback<BlockPos> callback) {
        this.callback = callback;
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

    // to be called only after checking coordinatesAreValid
    public BlockPos getNewTarget() {
        return new BlockPos(xValue, 0, zValue);
    }

    public boolean coordinatesAreValid() {
        String x = xInput.getText();
        String z = zInput.getText();
        if (Util.endsWithAny(x, "D", "d", "F", "f") || Util.endsWithAny(z, "D", "d", "F", "f")) {
            return false;
        }
        try {
            xValue = Double.parseDouble(x);
            zValue = Double.parseDouble(z);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0 && coordinatesAreValid()) {
            close(getNewTarget());
        }
    }


    @Override
    public void initGui() {
        super.initGui();
        okButton = new GuiButton(0, (width / 2) - 75, (height / 4) * 3, 150, 20, okButtonLabel);
        buttonList.add(okButton);
        okButton.visible = true;
        okButton.enabled = false;
        xInput = new GuiTextField(0, fontRendererObj, (width / 2) - 50, (height / 2) - (height / 8), 100, 20);
        xInput.setFocused(true);
        xInput.setMaxStringLength(20);
        zInput = new GuiTextField(0, fontRendererObj, (width / 2) - 50, (height / 2), 100, 20);
        zInput.setMaxStringLength(20);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        this.drawCenteredString(fontRendererObj, title, width / 2, 15, Util.WHITE);
        okButton.drawButton(mc, mouseX, mouseY);
        okButton.enabled = coordinatesAreValid();
        if (!coordinatesAreValid() && !(xInput.getText().isEmpty() || zInput.getText().isEmpty())) {
            drawCenteredString(fontRendererObj, error, width / 2, (height / 2) + (height / 8), Util.RED);
        }
        xInput.drawTextBox();
        xInput.setEnabled(true);
        zInput.drawTextBox();
        zInput.setEnabled(true);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        xInput.mouseClicked(mouseX, mouseY, mouseButton);
        zInput.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        xInput.updateCursorCounter();
        zInput.updateCursorCounter();
    }


    public void close() {
        close(null);
    }

    public void close(BlockPos blockPos) {
        mc.displayGuiScreen(null);
        if (mc.currentScreen == null) {
            mc.setIngameFocus();
        }
        if (blockPos != null) {
            callback.run(blockPos);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == HotkeyStore.get(this).binding.getKeyCode()) {
            close();
        } else if (keyCode == Keyboard.KEY_TAB) {
            xInput.setFocused(!xInput.isFocused());
            zInput.setFocused(!zInput.isFocused());
        } else if (keyCode == Keyboard.KEY_RETURN && coordinatesAreValid()) {
            close(getNewTarget());
        } else {
            xInput.textboxKeyTyped(typedChar, keyCode);
            zInput.textboxKeyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

}
