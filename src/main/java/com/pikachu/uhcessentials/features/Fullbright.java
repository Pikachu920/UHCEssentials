package com.pikachu.uhcessentials.features;

import com.pikachu.uhcessentials.Main;
import com.pikachu.uhcessentials.hotkeys.Hotkey;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Fullbright {

    private boolean enabled = Main.getConfig().getBoolean("enabled", "Fullbright", false,
            "Controls whether or not fullbright is enabled");
    private Minecraft mc = Minecraft.getMinecraft();
    private float originalGamma = mc.gameSettings.gammaSetting;

    public Fullbright() {
        setEnabled(enabled);
        HotkeyStore.add(new Hotkey(this) {
            @Override
            public void onPress() {
                toggle();
                Main.getConfig().get("Fullbright", "enabled", false,
                        "Controls whether or not fullbright is enabled").set(enabled);
                Main.getConfig().save();
            }

            @Override
            public KeyBinding getKeyBinding() {
                return new KeyBinding("Toggle Fullbright", Keyboard.KEY_L, "UHC Essentials");
            }
        });
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            originalGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = Float.MAX_VALUE;
        } else {
            mc.gameSettings.gammaSetting = originalGamma;
        }
        this.enabled = enabled;
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

}
