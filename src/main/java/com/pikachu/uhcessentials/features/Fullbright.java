package com.pikachu.uhcessentials.features;

import com.pikachu.uhcessentials.UHCEssentials;
import com.pikachu.uhcessentials.hotkeys.Hotkey;
import com.pikachu.uhcessentials.hotkeys.HotkeyStore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class Fullbright {

    private boolean cleanColors;
    private boolean fullbrightApplied;
    private float[] cleanedColors = new float[3];

    private boolean enabled = UHCEssentials.getConfig().getBoolean("enabled", "Fullbright", false,
            "Controls whether or not fullbright is enabled");
    private Minecraft mc = Minecraft.getMinecraft();

    public Fullbright() {
        MinecraftForge.EVENT_BUS.register(this);
        setEnabled(enabled);
        HotkeyStore.add(new Hotkey(this) {
            @Override
            public void onPress() {
                toggle();
                UHCEssentials.getConfig().get("Fullbright", "enabled", false,
                        "Controls whether or not fullbright is enabled").set(enabled);
                UHCEssentials.getConfig().save();
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
        this.enabled = enabled;
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    public void init() {
        mc.entityRenderer = new FullbrightEntityRender(mc, mc.getResourceManager());
    }

    /*
    * This method removes the brightness fog gets when you have night vision by
    * removing night vision, recalculating and then saving the new colors
    * and applying them to the event that had the night vision brightness
    * if full bright was applied
    */
    @SubscribeEvent
    public void onFogRender(EntityViewRenderEvent.FogColors e) {
        if (fullbrightApplied) {
            if (cleanColors) {
                cleanColors = false;
                cleanedColors[0] = e.red;
                cleanedColors[1] = e.blue;
                cleanedColors[2] = e.green;
            } else {
                cleanColors = true;
                mc.thePlayer.removePotionEffect(Potion.nightVision.id);
                e.renderer.updateFogColor((float) e.renderPartialTicks);
                e.red = cleanedColors[0];
                e.blue = cleanedColors[1];
                e.green = cleanedColors[2];
            }
        }
    }

    /*
     * Unfortunately, this is the best way to do fullbright without messing w/ game settings
     * or asm (that i've found)
     */
    public class FullbrightEntityRender extends EntityRenderer {

        public FullbrightEntityRender(Minecraft mcIn, IResourceManager resourceManagerIn) {
            super(mcIn, resourceManagerIn);
        }

        @Override
        public void updateCameraAndRender(float p_181560_1_, long p_181560_2_) {
            if (enabled && mc.thePlayer != null && !mc.thePlayer.isPotionActive(Potion.nightVision)) {
                fullbrightApplied = true;
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE, 999, false, false));
                super.updateCameraAndRender(p_181560_1_, p_181560_2_);
                mc.thePlayer.removePotionEffect(Potion.nightVision.id);
            } else {
                fullbrightApplied = false;
                super.updateCameraAndRender(p_181560_1_, p_181560_2_);
            }
        }

    }

}
