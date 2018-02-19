package com.pikachu.uhcessentials.features;

import com.pikachu.uhcessentials.Main;
import com.pikachu.uhcessentials.hotkeys.Hotkey;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

public class Fullbright {

    private final float FULLBRIGHT_LEVEL = 2000;
    private boolean enabled = Main.getConfig().getBoolean("enabled", "Fullbright", false,
            "Controls whether or not fullbright is enabled");
    private Minecraft mc = Minecraft.getMinecraft();
    private float originalGamma = mc.gameSettings.gammaSetting;

    public Fullbright() {
        // to prevent from saving fullbright as the brightness setting
        Runtime.getRuntime().addShutdownHook(new Thread(() -> mc.gameSettings.gammaSetting = originalGamma));
        MinecraftForge.EVENT_BUS.register(this);
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
                return new KeyBinding("key.uhcessentials.fullbright", Keyboard.KEY_L, "key.uhcessentials.category");
            }
        });
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            originalGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = FULLBRIGHT_LEVEL;
        } else {
            mc.gameSettings.gammaSetting = originalGamma;
        }
        this.enabled = enabled;
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    // without this fullbright won't properly persist restarts
    @SubscribeEvent
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (enabled) {
            mc.gameSettings.gammaSetting = FULLBRIGHT_LEVEL;
        }
    }

}
