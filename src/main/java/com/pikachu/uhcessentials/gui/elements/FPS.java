package com.pikachu.uhcessentials.gui.elements;

import com.pikachu.uhcessentials.Util;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class FPS extends MovableWindow {

    private final int PADDING = 1;
    private String fps;

    public FPS() {
        super();
    }

    @Override
    public int getHeight() {
        return Util.heightOf(fps.toCharArray());
    }

    @Override
    public int getWidth() {
        return Util.widthOf(fps.toCharArray());
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
        return "FPS";
    }

    public void render(RenderGameOverlayEvent.Text event) {
        fps = Util.getFPS() + " FPS";
        Gui.drawRect(getX() - PADDING, getY(), getX() + getWidth() + PADDING, getY() + getHeight(), getColor());
        fontRenderer.drawStringWithShadow(fps, getX(), getY(), Util.WHITE);
    }

}