package com.pikachu.uhcessentials.gui.elements;

import com.pikachu.uhcessentials.Util;
import com.pikachu.uhcessentials.gui.base.MovableWindow;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class FPS extends MovableWindow {

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
        fps = Util.getFPS();
        fontRenderer.drawStringWithShadow(fps, getX(), getY(), 0xfffff);
    }

}