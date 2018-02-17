package com.pikachu.uhcessentials.gui.elements;

import com.pikachu.uhcessentials.Util;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class Biome extends MovableWindow {

    private final int PADDING = 1;
    private String biome;

    public Biome() {
        super();
    }

    @Override
    public int getHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    @Override
    public int getWidth() {
        return fontRenderer.getStringWidth(biome);
    }

    @Override
    public int getDefaultX() {
        return 110;
    }

    @Override
    public int getDefaultY() {
        return 110;
    }

    @Override
    public String getName() {
        return "biome";
    }

    public void render(RenderGameOverlayEvent.Text event) {
        EntityPlayerSP player = mc.thePlayer;
        biome = Util.getBiomeAt(player.posX, player.posZ);
        Gui.drawRect(getX() - PADDING, getY(), getX() + getWidth() + PADDING, getY() + getHeight(), getColor());
        fontRenderer.drawStringWithShadow(biome, getX(), getY(), Util.WHITE);
    }

}
