package com.pikachu.uhcessentials.gui.windows;

import com.pikachu.uhcessentials.UHCEssentials;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import com.pikachu.uhcessentials.gui.screens.CoordinatesScreen;
import com.pikachu.uhcessentials.utils.CustomCompass;
import com.pikachu.uhcessentials.utils.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Compass extends MovableWindow {

    private static BlockPos target = new BlockPos(0, 0, 0);

    private final ItemStack COMPASS_STACK = new ItemStack(UHCEssentials.CUSTOM_COMPASS);

    public Compass() {
        super("compass", 150, 150);
        new CoordinatesScreen(newPos -> target = newPos);
    }

    public static BlockPos getTarget() {
        return target;
    }

    @Override
    public int getWidth() {
        return 15;
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public void render(RenderGameOverlayEvent.Text event) {
        Util.drawItemStack(COMPASS_STACK, getX(), getY(), false);
    }

    @SubscribeEvent
    public void onTextureSwitch(TextureStitchEvent.Pre event) {
        event.map.setTextureEntry(UHCEssentials.MOD_ID + ":builtin/uhccompass", new CustomCompass());
    }

}
